package com.example.ecommercebackend.dto.payment.response;

import java.math.BigDecimal;

public class PaymentComplateDto {
    private String status;
    private String conversationId;
    private String paymentId;
    private BigDecimal paidPrice;
    private String basketId;
    private String currency;

    public PaymentComplateDto(String status, String conversationId, String paymentId, BigDecimal paidPrice, String basketId, String currency) {
        this.status = status;
        this.conversationId = conversationId;
        this.paymentId = paymentId;
        this.paidPrice = paidPrice;
        this.basketId = basketId;
        this.currency = currency;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
