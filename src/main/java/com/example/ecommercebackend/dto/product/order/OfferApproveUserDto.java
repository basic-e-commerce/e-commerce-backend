package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.shipping.ShipmentDto;

import java.math.BigDecimal;

public class OfferApproveUserDto {
    private ShipmentDto shipment;

    public OfferApproveUserDto(ShipmentDto shipment) {
        this.shipment = shipment;
    }

    public ShipmentDto getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentDto shipment) {
        this.shipment = shipment;
    }
}
