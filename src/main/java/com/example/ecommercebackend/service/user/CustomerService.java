package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.user.CustomerBuilder;
import com.example.ecommercebackend.config.EncryptionUtils;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.product.shipping.AddressReceiptDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.dto.user.customer.*;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.entity.user.*;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.card.CardRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.mail.MailService;
import com.example.ecommercebackend.service.product.order.OrderService;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import com.example.ecommercebackend.service.redis.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CustomerService {

    @Value("${domain.test}")
    private String domain;

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final CustomerBuilder customerBuilder;
    private final AddressService addressService;
    private final RedisService redisService;
    private final MailService mailService;
    private final OrderService orderService;
    private final ShippingAddressService shippingAddressService;

    public CustomerService(CustomerRepository customerRepository, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, CustomerBuilder customerBuilder, AddressService addressService, RedisService redisService, MailService mailService, OrderService orderService, ShippingAddressService shippingAddressService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.customerBuilder = customerBuilder;
        this.addressService = addressService;
        this.redisService = redisService;
        this.mailService = mailService;
        this.orderService = orderService;
        this.shippingAddressService = shippingAddressService;
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Customer "+ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Customer findByUsernameNull(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }


    public Customer createCustomer(CustomerCreateDto customerCreateDto){
        if (customerRepository.findByUsername(customerCreateDto.getUsername()).isPresent())
            throw new ResourceAlreadyExistException("Bu kullanıcı e postası kullanılmaktadır!");

        User user = userService.getUserByUsernameOrNull(customerCreateDto.getUsername());

        if (!customerCreateDto.getPassword().equals(customerCreateDto.getRePassword()))
            throw new BadRequestException(ExceptionMessage.PASSWORD_NOT_MATCHES.getMessage());

        Set<Role> roles = new HashSet<>();

        roles.add(roleService.findByRoleName("CUSTOMER"));

        String hashPassword = passwordEncoder.encode(customerCreateDto.getPassword());


        if (user == null) {

            Customer customer = customerBuilder.customerCreateDtoToCustomer(customerCreateDto,hashPassword,roles);
            Customer save = customerRepository.save(customer);

            Card card = new Card(save);
            save.setCard(card);


            sendCustomerVerificationMail(save);


            return customerRepository.save(save);
        }else {
            if (user instanceof Guest guest){
                Customer customer = customerBuilder.customerCreateDtoToCustomer(customerCreateDto,hashPassword,roles);

                List<Order> guestOrders = orderService.changeSuccessGuestNull(guest);
                userService.deleteUser(guest);

                Customer save = customerRepository.save(customer);
                orderService.changeSuccessCustomerOrder(save,guestOrders);

                Card card = new Card(save);
                save.setCard(card);

                sendCustomerVerificationMail(save);

                return customerRepository.save(save);
            }else
                throw new ResourceAlreadyExistException("Bu e posta kullanılmaktadır");
        }
    }


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void sendCustomerVerificationMail(Customer customer) {

        String generateCode = String.valueOf(100000 + (int)(Math.random() * 900000));

        String link = domain + "/api/v1/auth/verification/" + generateCode;
        String htmlContent = """
        <!DOCTYPE html>
        <html lang="tr">
        <head>
          <meta charset="UTF-8">
          <title>Hesabınızı Onaylayın</title>
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <style>
            body {
              font-family: Arial, sans-serif;
              background-color: #f4f6f8;
              margin: 0;
              padding: 0;
            }
            .container {
              max-width: 600px;
              margin: auto;
              background-color: #ffffff;
              padding: 30px;
              border-radius: 10px;
              box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            }
            .button {
              background-color: #007bff;
              color: white;
              padding: 12px 24px;
              text-decoration: none;
              border-radius: 5px;
              display: inline-block;
              margin-top: 20px;
            }
            .footer {
              margin-top: 40px;
              font-size: 12px;
              color: #888;
              text-align: center;
            }
          </style>
        </head>
        <body>
          <div class="container">
            <h2>Merhaba,</h2>
            <p>Hesabınızı başarıyla oluşturduğunuz için teşekkür ederiz.</p>
            <p>Lütfen aşağıdaki butona tıklayarak e-posta adresinizi onaylayın:</p>
            <a href="%s" class="button">E-posta Adresini Onayla</a>
            <p>Eğer bu e-postayı siz istemediyseniz, bu mesajı yok sayabilirsiniz.</p>
            <div class="footer">
              © 2025 Litysoft Tüm hakları saklıdır.
            </div>
          </div>
        </body>
        </html>
        """.formatted(link); // 'link' burada onay linkiniz

        String onayKodu = mailService.send(
                customer.getUsername(),
                "Onay Kodu",
                htmlContent
        );

        redisService.saveData(generateCode,customer.getUsername(), Duration.ofDays(1));
        System.out.println(onayKodu);

        System.out.println(domain+"/api/v1/auth/verification/"+generateCode);
    }

    public String reSendVerificationMail(@NotNullParam String username){
        Customer customer = findByUsername(username);

        if (customer.isEnabled())
            throw new BadRequestException("Hesabınız zaten onaylanmıştır!");

        if (redisService.getData(customer.getUsername()) != null)
            throw new ResourceAlreadyExistException("Lütfen Mail Kutunuzu kontrol ediniz!");

        sendCustomerVerificationMail(customer);
        return "Mail tekrar gönderildi!";
    }

    // -------------- address --------------------

    @Transactional
    public AddressDetailDto createAddress(AddressCreateDto addressCreateDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Customer customer){
            Customer customer1 = findByUsername(customer.getUsername());
            String newTitle = addressCreateDto.getTitle().trim().toLowerCase();

            boolean titleExists = customer1.getAddresses().stream()
                    .anyMatch(address -> address.getTitle() != null &&
                            address.getTitle().trim().equalsIgnoreCase(newTitle));

            if (titleExists)
                throw new ResourceAlreadyExistException("Address "+ExceptionMessage.ALREADY_EXISTS.getMessage());

            Address address = addressService.createAddress(addressCreateDto,true);
            customer1.getAddresses().add(address);
            customerRepository.save(customer1);

            AddressApiDto addressApiDto = new AddressApiDto(
                    address.getFirstName() + " "+ address.getLastName(),
                    customer.getUsername(),
                    EncryptionUtils.decrypt(address.getPhoneNo()),
                    EncryptionUtils.decrypt(address.getAddressLine1()),
                    "",
                    address.getCountry().getIso(),
                    address.getCity().getName(),
                    address.getCity().getCityCode(),
                    address.getDistrict().getName(),
                    address.getDistrict().getDistrictId(),
                    address.getPostalCode(),
                    true,
                    address.getFirstName() + "-"+ address.getLastName()+"-"+UUID.randomUUID()
            );

            Address save = addressService.save(address);
            return new AddressDetailDto(address.getId(),
                    save.getTitle(),
                    save.getFirstName(),
                    save.getLastName(),
                    save.getUsername(),
                    save.getCountry().getUpperName(),
                    save.getCountry().getIso(),
                    save.getCity().getName(),
                    save.getCity().getCityCode(),
                    save.getDistrict().getName(),
                    save.getDistrict().getDistrictId(),
                    save.getPostalCode(),
                    save.getPhoneNo(),
                    save.getAddressLine1(),
                    save.getGeliverId()
            );
        }else
            throw new BadRequestException("Customer Not Authenticated");
    }

    public Set<AddressDetailDto> getAddresses(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Customer customer){
            Customer customer1 = findByUsername(customer.getUsername());
            Set<Address> addresses = customer1.getAddresses();
            return addresses.stream().map(x-> {
                return new AddressDetailDto(x.getId(),
                        x.getTitle(),
                        x.getFirstName(),x.getLastName(),
                        x.getUsername(),
                        x.getCountry().getUpperName(),
                        x.getCountry().getIso(),
                        x.getCity().getName(),
                        x.getCity().getCityCode(),
                        x.getDistrict().getName(),
                        x.getDistrict().getDistrictId(),
                        x.getPostalCode(),
                        EncryptionUtils.decrypt(x.getPhoneNo()),
                        EncryptionUtils.decrypt(x.getAddressLine1()),
                        x.getGeliverId());
            }).collect(Collectors.toSet());
        }else
            throw new BadRequestException("Customer Not Authenticated");

    }

    @Transactional
    public AddressDetailDto updateAddress(Integer addressId, AddressCreateDto addressCreateDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Customer customer){
            Customer customer1 = findByUsername(customer.getUsername());

            Address targetAddress = customer1.getAddresses().stream()
                        .filter(address -> address.getId() == addressId)
                        .findFirst()
                        .orElseThrow(()-> new NotFoundException("Address "+ExceptionMessage.NOT_FOUND.getMessage()));

            customer1.getAddresses().remove(targetAddress);

            String newTitle = addressCreateDto.getTitle().trim().toLowerCase();
            boolean titleExists = customer1.getAddresses().stream()
                        .anyMatch(address -> address.getTitle() != null &&
                                address.getTitle().trim().equalsIgnoreCase(newTitle));
            if (titleExists)
                throw new ResourceAlreadyExistException("Address "+ExceptionMessage.ALREADY_EXISTS.getMessage());
            else
                customer1.getAddresses().add(targetAddress);

            Address updateAddress = addressService.updateAddressById(addressId, addressCreateDto);
            return new AddressDetailDto(
                    updateAddress.getId(),
                    updateAddress.getTitle(),
                    updateAddress.getFirstName(),
                    updateAddress.getLastName(),
                    updateAddress.getUsername(),
                    updateAddress.getCountry().getUpperName(),
                    updateAddress.getCountry().getIso(),
                    updateAddress.getCity().getName(),
                    updateAddress.getCity().getCityCode(),
                    updateAddress.getDistrict().getName(),
                    updateAddress.getDistrict().getDistrictId(),
                    updateAddress.getPostalCode(),
                    EncryptionUtils.decrypt(updateAddress.getPhoneNo()),
                    EncryptionUtils.decrypt(updateAddress.getAddressLine1()),
                    updateAddress.getGeliverId()
            );
        }else
            throw new BadRequestException("Customer Not Authenticated");
    }

    @Transactional
    public String deleteAddress(Integer addressId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer){
            Customer customer1 = findByUsername(customer.getUsername());
            Address address = addressService.findAddressById(addressId);
            boolean removed = customer1.getAddresses().removeIf(a -> a.getId() == address.getId());

            if (removed){
                customerRepository.save(customer1);
                return "address deleted";
            }else
                throw new NotFoundException("Address :"+ExceptionMessage.NOT_FOUND.getMessage());
        }else
            throw new BadRequestException("Customer Not Authenticated");

    }

    public Customer findById(Integer id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerResponseDto> getAll(CustomerFilterRequestDto customerFilterRequestDto) {
        Sort sort = Sort.unsorted();
        if (customerFilterRequestDto.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(customerFilterRequestDto.getSortDirection()), customerFilterRequestDto.getSortBy());
        }
        Specification<Customer> customerSpecification = Specification
                        .where(isEnable(customerFilterRequestDto.getEnable()))
                        .and(hasDateBetween(customerFilterRequestDto.getStartDate(),customerFilterRequestDto.getEndDate()));

        return customerRepository.findAll(customerSpecification,sort).stream().map(customerBuilder::customerToCustomerResponseDto).toList();
    }

    @Transactional
    public String updatePassword(@NotNullParam PasswordUpdateDto passwordUpdateDto) {
        if (passwordUpdateDto.getOldPassword().isEmpty() || passwordUpdateDto.getPassword().isEmpty() || passwordUpdateDto.getRePassword().isEmpty())
            throw new BadRequestException("Lütfen tüm dataları doldurunuz");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer){

            if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), customer.getPassword()))
                throw new BadRequestException("Eski Parolanız yanlıştır.");


            if (!passwordUpdateDto.getPassword().equals(passwordUpdateDto.getRePassword()))
                throw new BadRequestException("Parolalarınız eşleşmiyor");

            customer.setPassword(passwordEncoder.encode(passwordUpdateDto.getPassword()));
            customerRepository.save(customer);
            return "Password updated";
        }else
            throw new BadRequestException("Customer Not Authenticated");


    }

    public boolean existByPhoneNo(String phoneNo){
        return customerRepository.existsByPhoneNumber(EncryptionUtils.encrypt(phoneNo));
    }

    @Transactional
    public String updateCustomer(@NotNullParam CustomerUpdateDto customerUpdateDto) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer){

            if (customerUpdateDto.getName() != null)
                customer.setFirstName(customerUpdateDto.getName());

            if (customerUpdateDto.getLastName() != null)
                customer.setLastName(customerUpdateDto.getLastName());

            if (customerUpdateDto.getPhoneNumber() != null)
                if (!existByPhoneNo(customerUpdateDto.getPhoneNumber()))
                    customer.setPhoneNumber(customerUpdateDto.getPhoneNumber());

            customerRepository.save(customer);

            return "customer updated";
        }else
            throw new BadRequestException("Customer Not Authenticated");



    }

    public CustomerProfileDto getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer){
            String phone = "";
            if (customer.getPhoneNumber() != null){
                phone =EncryptionUtils.decrypt(customer.getPhoneNumber());
            }
            return new CustomerProfileDto(customer.getFirstName(),customer.getLastName(),customer.getUsername(),phone);
        }else
            throw new BadRequestException("Customer Not Authenticated");
    }

    public static Specification<Customer> hasDateBetween(Instant start, Instant end) {
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            if (start != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), start));
            }
            if (end != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), end));
            }
            return predicate;
        };
    }

    public List<Customer> getAllBetweenAddress(Instant startDate, Instant endDate) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        Specification<Customer> where = Specification.where(hasDateBetween(startDate, endDate)).and(isEnable(true));
        return customerRepository.findAll(where,sort);
    }

    public Specification<Customer> isEnable(Boolean isEnable) {
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("enabled"), isEnable);
    }
}
