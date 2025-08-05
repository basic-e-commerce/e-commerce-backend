package com.example.ecommercebackend.repository.product.card;

import com.example.ecommercebackend.entity.product.card.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface CardItemRepository extends JpaRepository<CardItem, Integer> {
    List<CardItem> findAllByUpdateAtBetween(Instant startDate, Instant endDate);

}
