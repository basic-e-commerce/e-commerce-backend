package com.example.ecommercebackend.entity.product.shipping;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "shipping_template")
public class ShippingTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_template_seq")
    @SequenceGenerator(name = "shipping_template_seq", sequenceName = "shipping_template_seq", allocationSize = 1)
    private int id;

    private String geliverId;

    private String name;
    private String languageCode;
    private String length;
    private String width;
    private String height;
    private String desi;
    private String oldDesi;
    private String distanceUnit;
    private String weight;
    private String oldWeight;
    private String massUnit;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;

    public ShippingTemplate(String geliverId, String name, String languageCode, String length, String width, String height, String desi, String oldDesi, String distanceUnit, String weight, String oldWeight, String massUnit, Boolean isActive, Instant createdAt, Instant updatedAt) {
        this.geliverId = geliverId;
        this.name = name;
        this.languageCode = languageCode;
        this.length = length;
        this.width = width;
        this.height = height;
        this.desi = desi;
        this.oldDesi = oldDesi;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.oldWeight = oldWeight;
        this.massUnit = massUnit;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ShippingTemplate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeliverId() {
        return geliverId;
    }

    public void setGeliverId(String geliverId) {
        this.geliverId = geliverId;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
