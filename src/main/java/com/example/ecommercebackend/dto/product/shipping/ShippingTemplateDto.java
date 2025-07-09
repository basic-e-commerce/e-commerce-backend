package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingTemplateDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("length")
    private String length;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
    @JsonProperty("desi")
    private String desi;
    @JsonProperty("oldDesi")
    private String oldDesi;
    @JsonProperty("distanceUnit")
    private String distanceUnit;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("oldWeight")
    private String oldWeight;
    @JsonProperty("massUnit")
    private String massUnit;
    @JsonProperty("isActive")
    private boolean active;
    @JsonProperty("name")
    private String name;
    @JsonProperty("LanguageCode")
    private String languageCode;

    public ShippingTemplateDto(String id, String createdAt, String updatedAt, String length, String width, String height, String desi, String oldDesi, String distanceUnit, String weight, String oldWeight, String massUnit, boolean active, String name, String languageCode) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.length = length;
        this.width = width;
        this.height = height;
        this.desi = desi;
        this.oldDesi = oldDesi;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.oldWeight = oldWeight;
        this.massUnit = massUnit;
        this.active = active;
        this.name = name;
        this.languageCode = languageCode;
    }

    public ShippingTemplateDto() {
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDesi() {
        return desi;
    }

    public void setDesi(String desi) {
        this.desi = desi;
    }

    public String getOldDesi() {
        return oldDesi;
    }

    public void setOldDesi(String oldDesi) {
        this.oldDesi = oldDesi;
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

    public String getOldWeight() {
        return oldWeight;
    }

    public void setOldWeight(String oldWeight) {
        this.oldWeight = oldWeight;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
