package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.builder.user.CustomerBuilder;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.dto.user.customer.CustomerCreateDto;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.card.CardRepository;
import com.example.ecommercebackend.repository.user.AddressRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.mail.MailService;
import com.example.ecommercebackend.service.redis.RedisService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    public CustomerService(CustomerRepository customerRepository, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, CustomerBuilder customerBuilder, CardRepository cardRepository, AddressService addressService, RedisService redisService, MailService mailService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.customerBuilder = customerBuilder;
        this.cardRepository = cardRepository;
        this.addressService = addressService;
        this.redisService = redisService;
        this.mailService = mailService;
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Customer "+ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Customer createCustomer(CustomerCreateDto customerCreateDto){

        if (userService.isUserExistByUsername(customerCreateDto.getUsername()))
            throw new ResourceAlreadyExistException("Customer "+ExceptionMessage.ALREADY_EXISTS.getMessage());

        if (!customerCreateDto.getPassword().equals(customerCreateDto.getRePassword()))
            throw new BadRequestException(ExceptionMessage.PASSWORD_NOT_MATCHES.getMessage());

        String hashPassword = passwordEncoder.encode(customerCreateDto.getPassword());
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName("CUSTOMER"));
        Customer customer = customerBuilder.customerCreateDtoToCustomer(customerCreateDto,hashPassword,roles);

        Customer save = customerRepository.save(customer);
        Card card = new Card(save);
        cardRepository.save(card);
        save.setCard(card);

        String generateCode = String.valueOf(100000 + (int)(Math.random() * 900000));
        redisService.saveData(generateCode,customer.getUsername(), Duration.ofMinutes(30));
        System.out.println("----------"+customer.getUsername());
        System.out.println(mailService.send(customer.getUsername(),"Onay Kodu",domain+"/api/v1/auth/verification/"+generateCode));
        System.out.println(domain+"/api/v1/auth/verification/"+generateCode);
        return customerRepository.save(save);
    }


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
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
            return new AddressDetailDto(address.getId(),address.getTitle(),address.getCountry().getName(),address.getCity(),address.getPostalCode(),address.getPhoneNo(),address.getAddressLine1());
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
                        x.getCountry().getName(),
                        x.getCity(),
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
            return new AddressDetailDto(updateAddress.getId(),updateAddress.getTitle(),updateAddress.getCountry().getName(),updateAddress.getCity(),updateAddress.getPostalCode(),updateAddress.getPhoneNo(),updateAddress.getAddressLine1());
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
}
