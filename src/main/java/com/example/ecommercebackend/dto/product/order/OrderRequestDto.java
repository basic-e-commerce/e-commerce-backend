package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemRequestDto;

import java.util.List;

public class OrderRequestDto {
    List<OrderItemRequestDto> orderItems;

    public OrderRequestDto(List<OrderItemRequestDto> orderItems) {
        this.orderItems = orderItems;
    }
    public List<OrderItemRequestDto> getOrderItems() {
        return orderItems;
    }
}
