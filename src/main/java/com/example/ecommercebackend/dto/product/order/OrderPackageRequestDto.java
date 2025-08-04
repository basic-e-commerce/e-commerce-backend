package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.entity.product.products.ProductTemplate;

import java.math.BigDecimal;

public class OrderPackageRequestDto {

    private String packageName;
    private double length;  // cm
    private double width;
    private double height;
    private double weight;  // kg
    private ProductTemplate.DistanceUnit distanceUnit;
    private ProductTemplate.MassUnit massUnit;
    private String responsiveLabelURL;
    private OrderPackage.CargoCompany cargoCompany;
    private String cargoId;
    private String location;
    private String barcode;
    private BigDecimal amount;

    public OrderPackageRequestDto(String packageName, double length, double width, double height, double weight, ProductTemplate.DistanceUnit distanceUnit, ProductTemplate.MassUnit massUnit, String responsiveLabelURL, OrderPackage.CargoCompany cargoCompany, String cargoId, String location, String barcode, BigDecimal amount) {
        this.packageName = packageName;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.distanceUnit = distanceUnit;
        this.massUnit = massUnit;
        this.responsiveLabelURL = responsiveLabelURL;
        this.cargoCompany = cargoCompany;
        this.cargoId = cargoId;
        this.location = location;
        this.barcode = barcode;
        this.amount = amount;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getResponsiveLabelURL() {
        return responsiveLabelURL;
    }

    public void setResponsiveLabelURL(String responsiveLabelURL) {
        this.responsiveLabelURL = responsiveLabelURL;
    }

    public OrderPackage.CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
    }

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ProductTemplate.DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(ProductTemplate.DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public ProductTemplate.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(ProductTemplate.MassUnit massUnit) {
        this.massUnit = massUnit;
    }
}
