package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.user.CustomerBuilder;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.dto.user.customer.CustomerCreateDto;
import com.example.ecommercebackend.dto.user.customer.CustomerProfileDto;
import com.example.ecommercebackend.dto.user.customer.CustomerUpdateDto;
import com.example.ecommercebackend.dto.user.customer.PasswordUpdateDto;
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
import com.example.ecommercebackend.service.redis.RedisService;
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
    private final CardRepository cardRepository;
    private final AddressService addressService;
    private final RedisService redisService;
    private final MailService mailService;
    private final OrderService orderService;
    private final GuestService guestService;

    public CustomerService(CustomerRepository customerRepository, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, CustomerBuilder customerBuilder, CardRepository cardRepository, AddressService addressService, RedisService redisService, MailService mailService, OrderService orderService, GuestService guestService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.customerBuilder = customerBuilder;
        this.cardRepository = cardRepository;
        this.addressService = addressService;
        this.redisService = redisService;
        this.mailService = mailService;
        this.orderService = orderService;
        this.guestService = guestService;
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Customer "+ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Customer findByUsernameNull(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }

    public Customer createCustomer(CustomerCreateDto customerCreateDto){
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
        redisService.saveData(generateCode,customer.getUsername(), Duration.ofMinutes(30));
        System.out.println("----------"+customer.getUsername());
        String link = domain + "/api/v1/auth/verification/" + generateCode;
        String onayKodu = mailService.send(customer.getUsername(), "Onay Kodu","<!DOCTYPE html>\n" +
                "<html lang=\"tr\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Hesabınızı Onaylayın</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f4f6f8;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    .container {\n" +
                "      max-width: 600px;\n" +
                "      margin: auto;\n" +
                "      background-color: #ffffff;\n" +
                "      padding: 30px;\n" +
                "      border-radius: 10px;\n" +
                "      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);\n" +
                "    }\n" +
                "    .button {\n" +
                "      background-color: #007bff;\n" +
                "      color: white;\n" +
                "      padding: 12px 24px;\n" +
                "      text-decoration: none;\n" +
                "      border-radius: 5px;\n" +
                "      display: inline-block;\n" +
                "      margin-top: 20px;\n" +
                "    }\n" +
                "    .footer {\n" +
                "      margin-top: 40px;\n" +
                "      font-size: 12px;\n" +
                "      color: #888;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"container\">\n" +
                "    <h2>Merhaba,</h2>\n" +
                "    <p>Hesabınızı başarıyla oluşturduğunuz için teşekkür ederiz.</p>\n" +
                "    <p>Lütfen aşağıdaki butona tıklayarak e-posta adresinizi onaylayın:</p>\n" +
                "    \n" +
                "<a href=\"" + link + "\" class=\"button\">E-posta Adresini Onayla</a>\n" +
                "    \n" +
                "    <p>Eğer bu e-postayı siz istemediyseniz, bu mesajı yok sayabilirsiniz.</p>\n" +
                "\n" +
                "    <div class=\"footer\">\n" +
                "      © 2025 Litysoft Tüm hakları saklıdır.\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n" );
        System.out.println(onayKodu);
        System.out.println(domain+"/api/v1/auth/verification/"+generateCode);
    }

    // -------------- address --------------------

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

            Address address = addressService.createAddress(addressCreateDto);
            customer1.getAddresses().add(address);
            customerRepository.save(customer1);
            return new AddressDetailDto(address.getId(),address.getTitle(),address.getFirstName(),address.getLastName(),address.getCountry().getUpperName(),address.getCity().getName(),address.getDistrict().getName(),address.getPostalCode(),address.getPhoneNo(),address.getAddressLine1());
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
                        x.getCountry().getUpperName(),
                        x.getCity().getName(),
                        x.getDistrict().getName(),
                        x.getPostalCode(),
                        x.getPhoneNo(),
                        x.getAddressLine1());
            }).collect(Collectors.toSet());
        }else
            throw new BadRequestException("Customer Not Authenticated");

    }

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
            return new AddressDetailDto(updateAddress.getId(),updateAddress.getTitle(),updateAddress.getFirstName(),updateAddress.getLastName(),updateAddress.getCountry().getUpperName(),updateAddress.getCity().getName(),updateAddress.getDistrict().getName(),updateAddress.getPostalCode(),updateAddress.getPhoneNo(),updateAddress.getAddressLine1());
        }else
            throw new BadRequestException("Customer Not Authenticated");
    }

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

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

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
        return customerRepository.existsByPhoneNumber(phoneNo);
    }

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
                phone =customer.getPhoneNumber();
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
