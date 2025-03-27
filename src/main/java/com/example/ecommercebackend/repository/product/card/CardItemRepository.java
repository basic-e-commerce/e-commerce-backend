package com.example.ecommercebackend.repository.product.card;

import com.example.ecommercebackend.entity.product.card.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardItemRepository extends JpaRepository<CardItem, Integer> {
}
