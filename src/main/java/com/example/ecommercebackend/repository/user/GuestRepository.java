package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByUsername(String username);
}
