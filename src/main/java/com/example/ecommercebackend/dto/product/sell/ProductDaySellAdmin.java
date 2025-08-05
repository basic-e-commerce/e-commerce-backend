package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;
import java.util.List;

public class ProductDaySellAdmin {
    List<ProductDaySell> productDaySells;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Integer totalOrder;
    private BigDecimal averageOrderAmount;
    private BigDecimal refundPrice;
    private BigDecimal shippingFee;

    public ProductDaySellAdmin(List<ProductDaySell> productDaySells, Integer totalQuantity, BigDecimal totalPrice, Integer totalOrder, BigDecimal averageOrderAmount, BigDecimal refundPrice, BigDecimal shippingFee) {
        this.productDaySells = productDaySells;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.totalOrder = totalOrder;
        this.averageOrderAmount = averageOrderAmount;
        this.refundPrice = refundPrice;
        this.shippingFee = shippingFee;
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

    public BigDecimal getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }
}
