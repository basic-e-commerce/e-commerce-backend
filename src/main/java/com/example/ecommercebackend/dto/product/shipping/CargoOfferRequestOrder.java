package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CargoOfferRequestOrder {
    @JsonProperty("sourceCode")
    private String sourceCode;

    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("totalAmountCurrency")
    private String totalAmountCurrency;

    public CargoOfferRequestOrder(String sourceCode, String sourceIdentifier, String orderNumber, BigDecimal totalAmount, String totalAmountCurrency) {
        this.sourceCode = sourceCode;
        this.sourceIdentifier = sourceIdentifier;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.totalAmountCurrency = totalAmountCurrency;
    }

    public CargoOfferRequestOrder() {
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountCurrency() {
        return totalAmountCurrency;
    }

    public void setTotalAmountCurrency(String totalAmountCurrency) {
        this.totalAmountCurrency = totalAmountCurrency;
    }
}
