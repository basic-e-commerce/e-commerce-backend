package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;

import java.util.Set;

public class OrderPackageResponseDto {
    private int id;
    private Set<OrderItemResponseDto> orderItems;
    private String shipmentId;
    private String statusCode;
    private String cargoId;
    private String cargoCompanyName;
    private String cargoStatus;
    private String location;
    private String updateAt;
    private Boolean isCancel;
    private Boolean isMAnuel;


    public OrderPackageResponseDto(int id, Set<OrderItemResponseDto> orderItems, String shipmentId, String statusCode, String cargoId, String cargoCompanyName, String cargoStatus, String location, String updateAt, Boolean isCancel, Boolean isMAnuel) {
        this.id = id;
        this.orderItems = orderItems;
        this.shipmentId = shipmentId;
        this.statusCode = statusCode;
        this.cargoId = cargoId;
        this.cargoCompanyName = cargoCompanyName;
        this.cargoStatus = cargoStatus;
        this.location = location;
        this.updateAt = updateAt;
        this.isCancel = isCancel;
        this.isMAnuel = isMAnuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<OrderItemResponseDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemResponseDto> orderItems) {
        this.orderItems = orderItems;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoCompanyName() {
        return cargoCompanyName;
    }

    public void setCargoCompanyName(String cargoCompanyName) {
        this.cargoCompanyName = cargoCompanyName;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getCancel() {
        return isCancel;
    }

    public void setCancel(Boolean cancel) {
        isCancel = cancel;
    }

    public Boolean getMAnuel() {
        return isMAnuel;
    }

    public void setMAnuel(Boolean MAnuel) {
        isMAnuel = MAnuel;
    }
}
