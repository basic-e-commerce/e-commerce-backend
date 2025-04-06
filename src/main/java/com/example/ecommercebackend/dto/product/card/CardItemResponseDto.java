package com.example.ecommercebackend.dto.product.card;

import java.math.BigDecimal;

public class CardItemResponseDto {
    private int productId;
    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;

    public CardItemResponseDto(int productId, String productName, BigDecimal productPrice, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
