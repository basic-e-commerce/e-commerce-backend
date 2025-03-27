package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
