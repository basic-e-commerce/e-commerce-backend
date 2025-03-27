package com.example.ecommercebackend.repository.product.card;

import com.example.ecommercebackend.entity.product.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
