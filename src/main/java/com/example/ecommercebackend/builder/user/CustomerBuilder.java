package com.example.ecommercebackend.builder.user;

import com.example.ecommercebackend.dto.user.customer.CustomerCreateDto;
import com.example.ecommercebackend.dto.user.customer.CustomerResponseDto;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
public class CustomerBuilder {
    public Customer customerCreateDtoToCustomer(CustomerCreateDto customerCreateDto, String hashpassword, Set<Role> roles) {
        return new Customer(
                customerCreateDto.getFirstName(),
                customerCreateDto.getLastName(),
                null,
                customerCreateDto.getUsername(),
                hashpassword,
                roles,
                false,
                false
        );
    }

    public CustomerResponseDto customerToCustomerResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getUsername(),
                customer.getCreatedAt().atZone(ZoneId.of("Europe/Istanbul")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}
