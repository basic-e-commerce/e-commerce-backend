package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.dto.product.order.ShipmentUserDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoBuyRequestDto {
    @JsonProperty("providerServiceCode")
    private OrderPackage.CargoCompany cargoCompany;

    @JsonProperty("shipment")
    private ShipmentBuyRequestDto shipment;

    public CargoBuyRequestDto(OrderPackage.CargoCompany cargoCompany, ShipmentBuyRequestDto shipment) {
        this.cargoCompany = cargoCompany;
        this.shipment = shipment;
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
