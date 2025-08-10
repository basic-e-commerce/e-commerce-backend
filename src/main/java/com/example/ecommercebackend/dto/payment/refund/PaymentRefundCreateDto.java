package com.example.ecommercebackend.dto.payment.refund;

import java.math.BigDecimal;

public class PaymentRefundCreateDto {
    private BigDecimal refundAmount;
    private String orderCode;

    public PaymentRefundCreateDto(BigDecimal refundAmount, String orderCode) {
        this.refundAmount = refundAmount;
        this.orderCode = orderCode;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
