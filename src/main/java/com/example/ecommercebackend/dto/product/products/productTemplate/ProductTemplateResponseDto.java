package com.example.ecommercebackend.dto.product.products.productTemplate;

import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProductTemplateResponseDto {
    private String name;
    private Double length;  // uzunluk
    private Double width;  // genişlik
    private Double height;  // yükseklik
    private Double desi;    // hacim
    private ProductTemplate.DistanceUnit distanceUnit;   // uzunluk tipi
    private Double weight;    // ürünün ağırlıpı
    private ProductTemplate.MassUnit massUnit;   // ağırlık tipi
    private Boolean isActive;

    public ProductTemplateResponseDto(String name, Double length, Double width, Double height, Double desi, ProductTemplate.DistanceUnit distanceUnit, Double weight, ProductTemplate.MassUnit massUnit, Boolean isActive) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.desi = desi;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.massUnit = massUnit;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDesi() {
        return desi;
    }

    public void setDesi(Double desi) {
        this.desi = desi;
    }

    public ProductTemplate.DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(ProductTemplate.DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public ProductTemplate.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(ProductTemplate.MassUnit massUnit) {
        this.massUnit = massUnit;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
