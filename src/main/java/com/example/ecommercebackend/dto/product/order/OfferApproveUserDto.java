package com.example.ecommercebackend.dto.product.order;

public class OfferApproveUserDto {
    private ShipmentUserDto shipment;

    public OfferApproveUserDto(ShipmentUserDto shipment) {
        this.shipment = shipment;
    }

    public ShipmentUserDto getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentUserDto shipment) {
        this.shipment = shipment;
    }
}
