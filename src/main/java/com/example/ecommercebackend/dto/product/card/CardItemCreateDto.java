package com.example.ecommercebackend.dto.product.card;

public class CardItemCreateDto {
    private final int productId;
    private final int quantity;

    public CardItemCreateDto(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }


    public int getQuantity() {
        return quantity;
    }

}
