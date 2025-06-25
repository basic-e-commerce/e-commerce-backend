package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;

public class ProductDaySell {
    private BigDecimal price;
    private int quantity;
    private String date;

    public ProductDaySell(BigDecimal price, int quantity, String date) {
        this.price = price;
        this.quantity = quantity;
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
