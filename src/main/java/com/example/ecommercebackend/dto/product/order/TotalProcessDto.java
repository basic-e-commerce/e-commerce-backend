package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.entity.product.order.OrderItem;

import java.math.BigDecimal;
import java.util.Set;

public class TotalProcessDto {
    private BigDecimal totalPrice;
    Set<OrderItem> savedOrderItems;

    public TotalProcessDto(BigDecimal totalPrice, Set<OrderItem> savedOrderItems) {
        this.totalPrice = totalPrice;
        this.savedOrderItems = savedOrderItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<OrderItem> getSavedOrderItems() {
        return savedOrderItems;
    }

    public void setSavedOrderItems(Set<OrderItem> savedOrderItems) {
        this.savedOrderItems = savedOrderItems;
    }
}
