package com.example.ecommercebackend.dto.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferUserDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("totalAmount")
    private String totalAmount;
    @JsonProperty("discountRate")
    private String discountRate;
    @JsonProperty("providerCode")
    private String providerCode;
    @JsonProperty("providerServiceCode")
    private String providerServiceCode;
    @JsonProperty("averageEstimatedTimeHumanReadible")
    private String averageEstimatedTimeHumanReadible;
    @JsonProperty("rating")
    private double rating;

    public OfferUserDto(String id, String createdAt, String totalAmount, String discountRate, String providerCode, String providerServiceCode, String averageEstimatedTimeHumanReadible, double rating) {
        this.id = id;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.discountRate = discountRate;
        this.providerCode = providerCode;
        this.providerServiceCode = providerServiceCode;
        this.averageEstimatedTimeHumanReadible = averageEstimatedTimeHumanReadible;
        this.rating = rating;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
