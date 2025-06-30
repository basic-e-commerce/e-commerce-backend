package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.anotation.NotNullField;

public record OrderItemCreateDto(
        @NotNullField int productId,
        @NotNullField int quantity) {
}
