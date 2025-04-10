package com.example.ecommercebackend.dto.payment;

import java.math.BigDecimal;

public class PaymentCreditCardRequestDto {
    private String paymentMethod;
    private String orderCode;
    private CreditCardRequestDto creditCardRequestDto;
    private BigDecimal totalPrice;
    private Integer installmentNumber;


    public PaymentCreditCardRequestDto(String paymentMethod, String orderCode, CreditCardRequestDto creditCardRequestDto, BigDecimal totalPrice, Integer installmentNumber) {
        this.paymentMethod = paymentMethod;
        this.orderCode = orderCode;
        this.creditCardRequestDto = creditCardRequestDto;
        this.totalPrice = totalPrice;
        this.installmentNumber = installmentNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public CreditCardRequestDto getCreditCardRequestDto() {
        return creditCardRequestDto;
    }

    public void setCreditCardRequestDto(CreditCardRequestDto creditCardRequestDto) {
        this.creditCardRequestDto = creditCardRequestDto;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
}
