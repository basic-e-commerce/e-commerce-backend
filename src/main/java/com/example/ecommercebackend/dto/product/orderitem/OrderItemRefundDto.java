package com.example.ecommercebackend.dto.product.orderitem;

import com.example.ecommercebackend.anotation.NotNullField;

public record OrderItemRefundDto (
        @NotNullField int orderItemId,
        @NotNullField int productId,
        @NotNullField int quantity){
}
