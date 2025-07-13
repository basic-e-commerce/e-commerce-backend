package com.example.ecommercebackend.dto.product.shipping;

import java.math.BigDecimal;

public class CargoOffer {
    private BigDecimal amount;
    private String currency;
    private BigDecimal amountLocal;
    private BigDecimal amountVat;
    private BigDecimal amountLocalVat;
    private BigDecimal amountTax;
    private BigDecimal amountLocalTax;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountLocal;
    private String providerCode;
    private String providerServiceCode;
    private String transportType;

    public CargoOffer(BigDecimal amount, String currency, BigDecimal amountLocal, BigDecimal amountVat, BigDecimal amountLocalVat, BigDecimal amountTax, BigDecimal amountLocalTax, BigDecimal totalAmount, BigDecimal totalAmountLocal, String providerCode, String providerServiceCode, String transportType) {
        this.amount = amount;
        this.currency = currency;
        this.amountLocal = amountLocal;
        this.amountVat = amountVat;
        this.amountLocalVat = amountLocalVat;
        this.amountTax = amountTax;
        this.amountLocalTax = amountLocalTax;
        this.totalAmount = totalAmount;
        this.totalAmountLocal = totalAmountLocal;
        this.providerCode = providerCode;
        this.providerServiceCode = providerServiceCode;
        this.transportType = transportType;
    }

    public CargoOffer() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmountLocal() {
        return amountLocal;
    }

    public void setAmountLocal(BigDecimal amountLocal) {
        this.amountLocal = amountLocal;
    }

    public BigDecimal getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(BigDecimal amountVat) {
        this.amountVat = amountVat;
    }

    public BigDecimal getAmountLocalVat() {
        return amountLocalVat;
    }

    public void setAmountLocalVat(BigDecimal amountLocalVat) {
        this.amountLocalVat = amountLocalVat;
    }

    public BigDecimal getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(BigDecimal amountTax) {
        this.amountTax = amountTax;
    }

    public BigDecimal getAmountLocalTax() {
        return amountLocalTax;
    }

    public void setAmountLocalTax(BigDecimal amountLocalTax) {
        this.amountLocalTax = amountLocalTax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmountLocal() {
        return totalAmountLocal;
    }

    public void setTotalAmountLocal(BigDecimal totalAmountLocal) {
        this.totalAmountLocal = totalAmountLocal;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderServiceCode() {
        return providerServiceCode;
    }

    public void setProviderServiceCode(String providerServiceCode) {
        this.providerServiceCode = providerServiceCode;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
}
