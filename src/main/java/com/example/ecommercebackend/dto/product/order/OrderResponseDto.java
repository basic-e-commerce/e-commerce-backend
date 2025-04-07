package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseDto {
    private String orderCode;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDto> orderItemResponseDtos;

    public OrderResponseDto(String orderCode, BigDecimal totalPrice, List<OrderItemResponseDto> orderItemResponseDtos) {
        this.orderCode = orderCode;
        this.totalPrice = totalPrice;
        this.orderItemResponseDtos = orderItemResponseDtos;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemResponseDto> getOrderItemResponseDtos() {
        return orderItemResponseDtos;
    }

    public void setOrderItemResponseDtos(List<OrderItemResponseDto> orderItemResponseDtos) {
        this.orderItemResponseDtos = orderItemResponseDtos;
    }
}
