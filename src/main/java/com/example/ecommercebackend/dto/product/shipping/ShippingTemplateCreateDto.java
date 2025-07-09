package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.anotation.NotNullField;

public class ShippingTemplateCreateDto {
    @NotNullField
    private String name;
    @NotNullField
    private String distanceUnit;
    @NotNullField
    private String massUnit;
    @NotNullField
    private Double height;
    @NotNullField
    private Double length;
    @NotNullField
    private Double weight;
    @NotNullField
    private Double width;

    public ShippingTemplateCreateDto(String name, String distanceUnit, String massUnit, Double height, Double length, Double weight, Double width) {
        this.name = name;
        this.distanceUnit = distanceUnit;
        this.massUnit = massUnit;
        this.height = height;
        this.length = length;
        this.weight = weight;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }
}
