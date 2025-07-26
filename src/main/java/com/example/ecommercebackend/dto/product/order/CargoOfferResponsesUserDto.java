package com.example.ecommercebackend.dto.product.order;

import java.util.List;

public class CargoOfferResponsesUserDto {
    CargoOfferResponseUserDto cargoOfferResponseUserDto;

    public CargoOfferResponsesUserDto(CargoOfferResponseUserDto cargoOfferResponseUserDto) {
        this.cargoOfferResponseUserDto = cargoOfferResponseUserDto;
    }

    public CargoOfferResponseUserDto getCargoOfferResponseUserDto() {
        return cargoOfferResponseUserDto;
    }

    public void setCargoOfferResponseUserDto(CargoOfferResponseUserDto cargoOfferResponseUserDto) {
        this.cargoOfferResponseUserDto = cargoOfferResponseUserDto;
    }
}
