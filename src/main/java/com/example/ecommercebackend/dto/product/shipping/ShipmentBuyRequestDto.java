package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShipmentBuyRequestDto {
    @JsonProperty("test")
    private boolean test;

    @JsonProperty("senderAddressID")
    private String senderAddressId;

    @JsonProperty("returnAddressID")
    private String returnAddressId;

    @JsonProperty("recipientAddress")
    private CargoBuyRecipientAddress recipientAddress;

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

    @JsonProperty("productPaymentOnDelivery")
    private boolean productPaymentOnDelivery;

    @JsonProperty("hidePackageContentOnTag")
    private boolean hidePackageContentOnTag;

    @JsonProperty("order")
    private CargoOfferRequestOrder order;

    public ShipmentBuyRequestDto(boolean test, String senderAddressId, String returnAddressId, CargoBuyRecipientAddress recipientAddress, String length, String height, String width, String distanceUnit, String weight, String massUnit, List<CargoOfferRequestItem> items, boolean productPaymentOnDelivery, boolean hidePackageContentOnTag, CargoOfferRequestOrder order) {
        this.test = test;
        this.senderAddressId = senderAddressId;
        this.returnAddressId = returnAddressId;
        this.recipientAddress = recipientAddress;
        this.length = length;
        this.height = height;
        this.width = width;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.massUnit = massUnit;
        this.items = items;
        this.productPaymentOnDelivery = productPaymentOnDelivery;
        this.hidePackageContentOnTag = hidePackageContentOnTag;
        this.order = order;
    }

    public ShipmentBuyRequestDto() {
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public CargoBuyRecipientAddress getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(CargoBuyRecipientAddress recipientAddress) {
        this.recipientAddress = recipientAddress;
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

    public boolean isProductPaymentOnDelivery() {
        return productPaymentOnDelivery;
    }

    public void setProductPaymentOnDelivery(boolean productPaymentOnDelivery) {
        this.productPaymentOnDelivery = productPaymentOnDelivery;
    }

    public boolean isHidePackageContentOnTag() {
        return hidePackageContentOnTag;
    }

    public void setHidePackageContentOnTag(boolean hidePackageContentOnTag) {
        this.hidePackageContentOnTag = hidePackageContentOnTag;
    }

    public CargoOfferRequestOrder getOrder() {
        return order;
    }

    public void setOrder(CargoOfferRequestOrder order) {
        this.order = order;
    }

    public String getSenderAddressId() {
        return senderAddressId;
    }

    public void setSenderAddressId(String senderAddressId) {
        this.senderAddressId = senderAddressId;
    }

    public String getReturnAddressId() {
        return returnAddressId;
    }

    public void setReturnAddressId(String returnAddressId) {
        this.returnAddressId = returnAddressId;
    }
}
