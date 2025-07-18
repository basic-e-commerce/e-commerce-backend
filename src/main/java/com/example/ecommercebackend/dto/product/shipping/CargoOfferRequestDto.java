package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public class CargoOfferRequestDto {
    @JsonProperty("test")
    private boolean test;

    @JsonProperty("senderAddressID")
    private String senderAddressID;

    @JsonProperty("returnAddressID")
    private String returnAddressID;

    @JsonProperty("length")
    private String length;

    @JsonProperty("height")
    private String height;

    @JsonProperty("width")
    private String width;

    @JsonProperty("distanceUnit")
    private String distanceUnit;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("massUnit")
    private String massUnit;

    @JsonProperty("items")
    private List<CargoOfferRequestItem> items;

    @JsonProperty("recipientAddress")
    private CargoOfferRequestRecipientAddress recipientAddress;

    @JsonProperty("productPaymentOnDelivery")
    private Boolean productPaymentOnDelivery;

    @JsonProperty("order")
    private CargoOfferRequestOrder order;

    public CargoOfferRequestDto(boolean test, String senderAddressID, String returnAddressID, String length, String height, String width, String distanceUnit, String weight, String massUnit, List<CargoOfferRequestItem> items, CargoOfferRequestRecipientAddress recipientAddress, Boolean productPaymentOnDelivery, CargoOfferRequestOrder order) {
        this.test = test;
        this.senderAddressID = senderAddressID;
        this.returnAddressID = returnAddressID;
        this.length = length;
        this.height = height;
        this.width = width;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.massUnit = massUnit;
        this.items = items;
        this.recipientAddress = recipientAddress;
        this.productPaymentOnDelivery = productPaymentOnDelivery;
        this.order = order;
    }

    public CargoOfferRequestDto() {
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getSenderAddressID() {
        return senderAddressID;
    }

    public void setSenderAddressID(String senderAddressID) {
        this.senderAddressID = senderAddressID;
    }

    public String getReturnAddressID() {
        return returnAddressID;
    }

    public void setReturnAddressID(String returnAddressID) {
        this.returnAddressID = returnAddressID;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public List<CargoOfferRequestItem> getItems() {
        return items;
    }

    public void setItems(List<CargoOfferRequestItem> items) {
        this.items = items;
    }

    public CargoOfferRequestRecipientAddress getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(CargoOfferRequestRecipientAddress recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Boolean getProductPaymentOnDelivery() {
        return productPaymentOnDelivery;
    }

    public void setProductPaymentOnDelivery(Boolean productPaymentOnDelivery) {
        this.productPaymentOnDelivery = productPaymentOnDelivery;
    }

    public CargoOfferRequestOrder getOrder() {
        return order;
    }

    public void setOrder(CargoOfferRequestOrder order) {
        this.order = order;
    }
}
