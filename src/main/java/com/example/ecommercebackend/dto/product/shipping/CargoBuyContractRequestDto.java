package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoBuyContractRequestDto {
    @JsonProperty("providerAccountID")
    private String providerAccountID;

    @JsonProperty("providerServiceCode")
    private OrderPackage.CargoCompany cargoCompany;

    @JsonProperty("shipment")
    private ShipmentBuyRequestDto shipment;

    public CargoBuyContractRequestDto(String providerAccountID, OrderPackage.CargoCompany cargoCompany, ShipmentBuyRequestDto shipment) {
        this.providerAccountID = providerAccountID;
        this.cargoCompany = cargoCompany;
        this.shipment = shipment;
    }

    public CargoBuyContractRequestDto() {
    }

    public String getProviderAccountID() {
        return providerAccountID;
    }

    public void setProviderAccountID(String providerAccountID) {
        this.providerAccountID = providerAccountID;
    }

    public OrderPackage.CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
    }

    public ShipmentBuyRequestDto getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentBuyRequestDto shipment) {
        this.shipment = shipment;
    }
}
