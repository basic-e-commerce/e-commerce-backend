package com.example.ecommercebackend.dto.product.card;

import java.util.List;

public class CardCreateDto {
    private List<CardItemCreateDto> cardItems;

    public CardCreateDto(List<CardItemCreateDto> cardItems) {
        this.cardItems = cardItems;
    }

    public List<CardItemCreateDto> getCardItems() {
        return cardItems;
    }
}
