package com.example.ecommercebackend.dto.product.card;

public class CardProductRequestDto {
    private int productId;


    public CardProductRequestDto(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
