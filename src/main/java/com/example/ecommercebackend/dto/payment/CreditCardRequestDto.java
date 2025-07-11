package com.example.ecommercebackend.dto.payment;

import com.example.ecommercebackend.anotation.NotNullField;

public class CreditCardRequestDto {
    @NotNullField
    private String cardNumber;
    @NotNullField
    private String cardHolderName;
    @NotNullField
    private String expirationMonth;
    @NotNullField
    private String expirationYear;
    @NotNullField
    private String cvv;

    public CreditCardRequestDto(String cardNumber, String cardHolderName, String expirationMonth, String expirationYear, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
