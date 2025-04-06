package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.builder.user.CustomerBuilder;
import com.example.ecommercebackend.dto.user.customer.CustomerCreateDto;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.card.CardRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final CustomerBuilder customerBuilder;
    private final CardRepository cardRepository;

    public CustomerService(CustomerRepository customerRepository, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, CustomerBuilder customerBuilder, CardRepository cardRepository) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.customerBuilder = customerBuilder;
        this.cardRepository = cardRepository;
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Customer "+ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Customer createCustomer(CustomerCreateDto customerCreateDto){

        if (userService.isUserExistByUsername(customerCreateDto.getUsername()))
            throw new ResourceAlreadyExistException("Customer "+ExceptionMessage.ALREADY_EXISTS.getMessage());

        if (userService.isUserExistByPhoneNumber(customerCreateDto.getPhoneNumber()))
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

        return customerRepository.save(save);
    }


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
