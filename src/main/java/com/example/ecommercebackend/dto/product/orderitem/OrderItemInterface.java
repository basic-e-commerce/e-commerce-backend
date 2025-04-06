package com.example.ecommercebackend.dto.product.orderitem;

import java.math.BigDecimal;

public interface OrderItemInterface {
    public int getId();
    public BigDecimal getPrice();
    public int getQuantity();
    interface ProductInfo {
        int getId(); // Product ID
        BigDecimal getComparePrice(); // Product Compare Price
    }
}
