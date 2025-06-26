package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;
import java.util.List;

public class ProductDaySellAdmin {
    List<ProductDaySell> productDaySells;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Integer totalOrder;
    private BigDecimal averageOrderAmount;

    public ProductDaySellAdmin(List<ProductDaySell> productDaySells, Integer totalQuantity, BigDecimal totalPrice, Integer totalOrder, BigDecimal averageOrderAmount) {
        this.productDaySells = productDaySells;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.totalOrder = totalOrder;
        this.averageOrderAmount = averageOrderAmount;
    }

    public List<ProductDaySell> getProductDaySells() {
        return productDaySells;
    }

    public void setProductDaySells(List<ProductDaySell> productDaySells) {
        this.productDaySells = productDaySells;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getAverageOrderAmount() {
        return averageOrderAmount;
    }

    public void setAverageOrderAmount(BigDecimal averageOrderAmount) {
        this.averageOrderAmount = averageOrderAmount;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }
}
