package com.example.ecommercebackend.builder.user;

import com.example.ecommercebackend.dto.user.customer.CustomerCreateDto;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomerBuilder {
    public Customer customerCreateDtoToCustomer(CustomerCreateDto customerCreateDto, String hashpassword, Set<Role> roles) {
        return new Customer(
                customerCreateDto.getFirstName(),
                customerCreateDto.getLastName(),
                customerCreateDto.getPhoneNumber(),
                customerCreateDto.getUsername(),
                hashpassword,
                roles,
                true,
                true
        );
    }
}
