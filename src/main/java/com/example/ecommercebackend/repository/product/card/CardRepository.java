package com.example.ecommercebackend.repository.product.card;

import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCustomer(Customer customer);
}
