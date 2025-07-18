package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderShippingDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("itemIDs")
    private String itemIDs;

    @JsonProperty("buyerShippingCost")
    private String buyerShippingCost;

    @JsonProperty("buyerShipmentMethod")
    private String buyerShipmentMethod;

    @JsonProperty("totalAmount")
    private String totalAmount;

    @JsonProperty("totalTax")
    private String totalTax;

    @JsonProperty("totalAmountCurrency")
    private String totalAmountCurrency;

    @JsonProperty("sourceCode")
    private String sourceCode;

    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("orderCode")
    private String orderCode;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("organizationID")
    private String organizationID;

    public OrderShippingDto(String id, String createdAt, String updatedAt, String itemIDs, String buyerShippingCost, String buyerShipmentMethod, String totalAmount, String totalTax, String totalAmountCurrency, String sourceCode, String sourceIdentifier, String orderNumber, String orderCode, String notes, String organizationID) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.itemIDs = itemIDs;
        this.buyerShippingCost = buyerShippingCost;
        this.buyerShipmentMethod = buyerShipmentMethod;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.totalAmountCurrency = totalAmountCurrency;
        this.sourceCode = sourceCode;
        this.sourceIdentifier = sourceIdentifier;
        this.orderNumber = orderNumber;
        this.orderCode = orderCode;
        this.notes = notes;
        this.organizationID = organizationID;
    }

    public OrderShippingDto() {
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

    public String getItemIDs() {
        return itemIDs;
    }

    public void setItemIDs(String itemIDs) {
        this.itemIDs = itemIDs;
    }

    public String getBuyerShippingCost() {
        return buyerShippingCost;
    }

    public void setBuyerShippingCost(String buyerShippingCost) {
        this.buyerShippingCost = buyerShippingCost;
    }

    public String getBuyerShipmentMethod() {
        return buyerShipmentMethod;
    }

    public void setBuyerShipmentMethod(String buyerShipmentMethod) {
        this.buyerShipmentMethod = buyerShipmentMethod;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotalAmountCurrency() {
        return totalAmountCurrency;
    }

    public void setTotalAmountCurrency(String totalAmountCurrency) {
        this.totalAmountCurrency = totalAmountCurrency;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }
}
