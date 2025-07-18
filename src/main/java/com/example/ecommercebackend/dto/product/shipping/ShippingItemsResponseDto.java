package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingItemsResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("title")
    private String title;

    @JsonProperty("variantTitle")
    private String variantTitle;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("unitWeight")
    private String unitWeight;

    @JsonProperty("totalPrice")
    private String totalPrice;

    @JsonProperty("totalPriceLocal")
    private String totalPriceLocal;

    @JsonProperty("massUnit")
    private String massUnit;

    @JsonProperty("unitPrice")
    private String unitPrice;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("unitPriceLocal")
    private String unitPriceLocal;

    @JsonProperty("currencyLocal")
    private String currencyLocal;

    @JsonProperty("countryOfOrigin")
    private String countryOfOrigin;

    @JsonProperty("maxShipTime")
    private String maxShipTime;

    @JsonProperty("maxDeliveryTime")
    private String maxDeliveryTime;

    @JsonProperty("sku")
    private String sku;

    public ShippingItemsResponseDto(String id, String createdAt, String updatedAt, String title, String variantTitle, int quantity, String unitWeight, String totalPrice, String totalPriceLocal, String massUnit, String unitPrice, String currency, String unitPriceLocal, String currencyLocal, String countryOfOrigin, String maxShipTime, String maxDeliveryTime, String sku) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.variantTitle = variantTitle;
        this.quantity = quantity;
        this.unitWeight = unitWeight;
        this.totalPrice = totalPrice;
        this.totalPriceLocal = totalPriceLocal;
        this.massUnit = massUnit;
        this.unitPrice = unitPrice;
        this.currency = currency;
        this.unitPriceLocal = unitPriceLocal;
        this.currencyLocal = currencyLocal;
        this.countryOfOrigin = countryOfOrigin;
        this.maxShipTime = maxShipTime;
        this.maxDeliveryTime = maxDeliveryTime;
        this.sku = sku;
    }

    public ShippingItemsResponseDto() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVariantTitle() {
        return variantTitle;
    }

    public void setVariantTitle(String variantTitle) {
        this.variantTitle = variantTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(String unitWeight) {
        this.unitWeight = unitWeight;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPriceLocal() {
        return totalPriceLocal;
    }

    public void setTotalPriceLocal(String totalPriceLocal) {
        this.totalPriceLocal = totalPriceLocal;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUnitPriceLocal() {
        return unitPriceLocal;
    }

    public void setUnitPriceLocal(String unitPriceLocal) {
        this.unitPriceLocal = unitPriceLocal;
    }

    public String getCurrencyLocal() {
        return currencyLocal;
    }

    public void setCurrencyLocal(String currencyLocal) {
        this.currencyLocal = currencyLocal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getMaxShipTime() {
        return maxShipTime;
    }

    public void setMaxShipTime(String maxShipTime) {
        this.maxShipTime = maxShipTime;
    }

    public String getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public void setMaxDeliveryTime(String maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
