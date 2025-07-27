package com.example.ecommercebackend.dto.payment.response;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public class OrderItemTansactionId {
    private String orderItemId;
    private String paymentTransactionId;
    private BigDecimal price;
    private BigDecimal paidPrice;
    private String basketId;


    public OrderItemTansactionId(String orderItemId, String paymentTransactionId, BigDecimal price, BigDecimal paidPrice, String basketId) {
        this.orderItemId = orderItemId;
        this.paymentTransactionId = paymentTransactionId;
        this.price = price;
        this.paidPrice = paidPrice;
        this.basketId = basketId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(BigDecimal paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getBasketId() {
        return basketId;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }
}
