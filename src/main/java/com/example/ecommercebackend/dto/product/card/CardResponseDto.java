package com.example.ecommercebackend.dto.product.card;

import java.util.List;

public class CardResponseDto {
    private int id;
    private String customerName;
    List<CardItemResponseDto> cardItemResponseDtos;

    public CardResponseDto(int id, String customerName, List<CardItemResponseDto> cardItemResponseDtos) {
        this.id = id;
        this.customerName = customerName;
        this.cardItemResponseDtos = cardItemResponseDtos;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<CardItemResponseDto> getCardItemResponseDtos() {
        return cardItemResponseDtos;
    }
}
