package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("amountLocal")
    private String amountLocal;

    @JsonProperty("currencyLocal")
    private String currencyLocal;

    @JsonProperty("amountVat")
    private String amountVat;

    @JsonProperty("amountLocalVat")
    private String amountLocalVat;

    @JsonProperty("amountTax")
    private String amountTax;

    @JsonProperty("amountLocalTax")
    private String amountLocalTax;

    @JsonProperty("totalAmount")
    private String totalAmount;

    @JsonProperty("totalAmountLocal")
    private String totalAmountLocal;

    @JsonProperty("amountOld")
    private String amountOld;

    @JsonProperty("amountLocalOld")
    private String amountLocalOld;

    @JsonProperty("discountRate")
    private String discountRate;

    @JsonProperty("bonusBalance")
    private String bonusBalance;

    @JsonProperty("providerCode")
    private String providerCode;

    @JsonProperty("providerServiceCode")
    private String providerServiceCode;

    @JsonProperty("providerAccountID")
    private String providerAccountID;

    @JsonProperty("averageEstimatedTimeHumanReadible")
    private String averageEstimatedTimeHumanReadible;

    @JsonProperty("averageEstimatedTime")
    private String averageEstimatedTime;

    @JsonProperty("durationTerms")
    private String durationTerms;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("isAccepted")
    private boolean isAccepted;

    @JsonProperty("isGlobal")
    private boolean isGlobal;

    @JsonProperty("isC2C")
    private boolean isC2C;

    @JsonProperty("integrationType")
    private String integrationType;

    @JsonProperty("isMainOffer")
    private boolean isMainOffer;

    @JsonProperty("isProviderAccountOffer")
    private boolean isProviderAccountOffer;

    @JsonProperty("providerAccountOwnerType")
    private String providerAccountOwnerType;

    @JsonProperty("providerAccountName")
    private String providerAccountName;

    public OfferDto(String id, String createdAt, String updatedAt, String amount, String currency, String amountLocal, String currencyLocal, String amountVat, String amountLocalVat, String amountTax, String amountLocalTax, String totalAmount, String totalAmountLocal, String amountOld, String amountLocalOld, String discountRate, String bonusBalance, String providerCode, String providerServiceCode, String providerAccountID, String averageEstimatedTimeHumanReadible, String averageEstimatedTime, String durationTerms, double rating, boolean isAccepted, boolean isGlobal, boolean isC2C, String integrationType, boolean isMainOffer, boolean isProviderAccountOffer, String providerAccountOwnerType, String providerAccountName) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.currency = currency;
        this.amountLocal = amountLocal;
        this.currencyLocal = currencyLocal;
        this.amountVat = amountVat;
        this.amountLocalVat = amountLocalVat;
        this.amountTax = amountTax;
        this.amountLocalTax = amountLocalTax;
        this.totalAmount = totalAmount;
        this.totalAmountLocal = totalAmountLocal;
        this.amountOld = amountOld;
        this.amountLocalOld = amountLocalOld;
        this.discountRate = discountRate;
        this.bonusBalance = bonusBalance;
        this.providerCode = providerCode;
        this.providerServiceCode = providerServiceCode;
        this.providerAccountID = providerAccountID;
        this.averageEstimatedTimeHumanReadible = averageEstimatedTimeHumanReadible;
        this.averageEstimatedTime = averageEstimatedTime;
        this.durationTerms = durationTerms;
        this.rating = rating;
        this.isAccepted = isAccepted;
        this.isGlobal = isGlobal;
        this.isC2C = isC2C;
        this.integrationType = integrationType;
        this.isMainOffer = isMainOffer;
        this.isProviderAccountOffer = isProviderAccountOffer;
        this.providerAccountOwnerType = providerAccountOwnerType;
        this.providerAccountName = providerAccountName;
    }

    public OfferDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountLocal() {
        return amountLocal;
    }

    public void setAmountLocal(String amountLocal) {
        this.amountLocal = amountLocal;
    }

    public String getCurrencyLocal() {
        return currencyLocal;
    }

    public void setCurrencyLocal(String currencyLocal) {
        this.currencyLocal = currencyLocal;
    }

    public String getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(String amountVat) {
        this.amountVat = amountVat;
    }

    public String getAmountLocalVat() {
        return amountLocalVat;
    }

    public void setAmountLocalVat(String amountLocalVat) {
        this.amountLocalVat = amountLocalVat;
    }

    public String getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(String amountTax) {
        this.amountTax = amountTax;
    }

    public String getAmountLocalTax() {
        return amountLocalTax;
    }

    public void setAmountLocalTax(String amountLocalTax) {
        this.amountLocalTax = amountLocalTax;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountLocal() {
        return totalAmountLocal;
    }

    public void setTotalAmountLocal(String totalAmountLocal) {
        this.totalAmountLocal = totalAmountLocal;
    }

    public String getAmountOld() {
        return amountOld;
    }

    public void setAmountOld(String amountOld) {
        this.amountOld = amountOld;
    }

    public String getAmountLocalOld() {
        return amountLocalOld;
    }

    public void setAmountLocalOld(String amountLocalOld) {
        this.amountLocalOld = amountLocalOld;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getBonusBalance() {
        return bonusBalance;
    }

    public void setBonusBalance(String bonusBalance) {
        this.bonusBalance = bonusBalance;
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

    public String getAverageEstimatedTimeHumanReadible() {
        return averageEstimatedTimeHumanReadible;
    }

    public void setAverageEstimatedTimeHumanReadible(String averageEstimatedTimeHumanReadible) {
        this.averageEstimatedTimeHumanReadible = averageEstimatedTimeHumanReadible;
    }

    public String getDurationTerms() {
        return durationTerms;
    }

    public void setDurationTerms(String durationTerms) {
        this.durationTerms = durationTerms;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public boolean isC2C() {
        return isC2C;
    }

    public void setC2C(boolean c2C) {
        isC2C = c2C;
    }

    public String getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    public boolean isMainOffer() {
        return isMainOffer;
    }

    public void setMainOffer(boolean mainOffer) {
        isMainOffer = mainOffer;
    }

    public boolean isProviderAccountOffer() {
        return isProviderAccountOffer;
    }

    public void setProviderAccountOffer(boolean providerAccountOffer) {
        isProviderAccountOffer = providerAccountOffer;
    }

    public String getProviderAccountOwnerType() {
        return providerAccountOwnerType;
    }

    public void setProviderAccountOwnerType(String providerAccountOwnerType) {
        this.providerAccountOwnerType = providerAccountOwnerType;
    }

    public String getProviderAccountName() {
        return providerAccountName;
    }

    public void setProviderAccountName(String providerAccountName) {
        this.providerAccountName = providerAccountName;
    }

    public String getProviderAccountID() {
        return providerAccountID;
    }

    public void setProviderAccountID(String providerAccountID) {
        this.providerAccountID = providerAccountID;
    }

    public String getAverageEstimatedTime() {
        return averageEstimatedTime;
    }

    public void setAverageEstimatedTime(String averageEstimatedTime) {
        this.averageEstimatedTime = averageEstimatedTime;
    }
}
