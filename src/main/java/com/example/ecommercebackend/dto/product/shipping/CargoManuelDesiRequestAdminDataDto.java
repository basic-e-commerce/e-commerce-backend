package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemRequestDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class CargoManuelDesiRequestAdminDataDto {

    @JsonProperty("cargoCompany")
    private OrderPackage.CargoCompany cargoCompany;

    @JsonProperty("length")
    private Double length;

    @JsonProperty("height")
    private Double height;

    @JsonProperty("width")
    private Double width;

    @JsonProperty("distanceUnit")
    private String distanceUnit;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("massUnit")
    private String massUnit;

    private List<OrderItemRequestDto> orderItems;

    private BigDecimal cargoFee;

    public CargoManuelDesiRequestAdminDataDto(OrderPackage.CargoCompany cargoCompany, Double length, Double height, Double width, String distanceUnit, Double weight, String massUnit, List<OrderItemRequestDto> orderItems, BigDecimal cargoFee) {
        this.cargoCompany = cargoCompany;
        this.length = length;
        this.height = height;
        this.width = width;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.massUnit = massUnit;
        this.orderItems = orderItems;
        this.cargoFee = cargoFee;
    }

    public OrderPackage.CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public List<OrderItemRequestDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequestDto> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getCargoFee() {
        return cargoFee;
    }

    public void setCargoFee(BigDecimal cargoFee) {
        this.cargoFee = cargoFee;
    }
}
