package com.example.ecommercebackend.dto.payment;

import com.example.ecommercebackend.anotation.NotNullField;

import java.math.BigDecimal;

public class PaymentCreditCardRequestDto {
    @NotNullField
    private String paymentMethod;
    @NotNullField
    private CreditCardRequestDto creditCardRequestDto;
    @NotNullField
    private Integer installmentNumber;


    public PaymentCreditCardRequestDto(String paymentMethod, CreditCardRequestDto creditCardRequestDto, Integer installmentNumber) {
        this.paymentMethod = paymentMethod;
        this.creditCardRequestDto = creditCardRequestDto;
        this.installmentNumber = installmentNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public CreditCardRequestDto getCreditCardRequestDto() {
        return creditCardRequestDto;
    }

    public void setCreditCardRequestDto(CreditCardRequestDto creditCardRequestDto) {
        this.creditCardRequestDto = creditCardRequestDto;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
}
