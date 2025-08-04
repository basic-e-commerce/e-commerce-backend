package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.entity.product.order.OrderPackage;

public class OrderPackageUpdateDto {
    private OrderPackage.OrderPackageStatusCode orderPackageStatusCode;
    private OrderPackage.CargoStatus cargoStatus;
    private String location;

    public OrderPackageUpdateDto(OrderPackage.OrderPackageStatusCode orderPackageStatusCode, OrderPackage.CargoStatus cargoStatus, String location) {
        this.orderPackageStatusCode = orderPackageStatusCode;
        this.cargoStatus = cargoStatus;
        this.location = location;
    }

    public OrderPackage.OrderPackageStatusCode getOrderPackageStatusCode() {
        return orderPackageStatusCode;
    }

    public void setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode orderPackageStatusCode) {
        this.orderPackageStatusCode = orderPackageStatusCode;
    }

    public OrderPackage.CargoStatus getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(OrderPackage.CargoStatus cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
