package com.example.ecommercebackend.dto.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentUserDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("test")
    private boolean test;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("labelFileType")
    private String labelFileType;

    @JsonProperty("labelURL")
    private String labelURL;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("responsiveLabelURL")
    private String responsiveLabelURL;

    public ShipmentUserDto(String id, String createdAt, String updatedAt, boolean test, String barcode, String labelFileType, String labelURL, String statusCode, String responsiveLabelURL) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.test = test;
        this.barcode = barcode;
        this.labelFileType = labelFileType;
        this.labelURL = labelURL;
        this.statusCode = statusCode;
        this.responsiveLabelURL = responsiveLabelURL;
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

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getLabelFileType() {
        return labelFileType;
    }

    public void setLabelFileType(String labelFileType) {
        this.labelFileType = labelFileType;
    }

    public String getLabelURL() {
        return labelURL;
    }

    public void setLabelURL(String labelURL) {
        this.labelURL = labelURL;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponsiveLabelURL() {
        return responsiveLabelURL;
    }

    public void setResponsiveLabelURL(String responsiveLabelURL) {
        this.responsiveLabelURL = responsiveLabelURL;
    }
}

