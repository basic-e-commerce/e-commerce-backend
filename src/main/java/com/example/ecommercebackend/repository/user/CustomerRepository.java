package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> , JpaSpecificationExecutor<Customer> {
    Optional<Customer> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByPhoneNumber(String phoneNumber);

}
