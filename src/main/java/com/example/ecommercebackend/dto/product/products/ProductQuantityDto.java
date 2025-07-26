package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.entity.product.products.Product;

import java.math.BigDecimal;

public class ProductQuantityDto {
    private Integer productId;
    private BigDecimal comparePrice;
    private BigDecimal taxRate;
    private int quantity;

    public ProductQuantityDto(Integer productId, BigDecimal comparePrice, BigDecimal taxRate, int quantity) {
        this.productId = productId;
        this.comparePrice = comparePrice;
        this.taxRate = taxRate;
        this.quantity = quantity;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(BigDecimal comparePrice) {
        this.comparePrice = comparePrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
