package com.example.ecommercebackend.dto.product.order;

import java.util.List;

public class CargoOfferResponsesUserDto {
    Integer orderPackageIds;
    CargoOfferResponseUserDto cargoOfferResponseUserDto;

    public CargoOfferResponsesUserDto(Integer orderPackageIds, CargoOfferResponseUserDto cargoOfferResponseUserDto) {
        this.orderPackageIds = orderPackageIds;
        this.cargoOfferResponseUserDto = cargoOfferResponseUserDto;
    }

    public Integer getOrderPackageIds() {
        return orderPackageIds;
    }

    public void setOrderPackageIds(Integer orderPackageIds) {
        this.orderPackageIds = orderPackageIds;
    }

    public CargoOfferResponseUserDto getCargoOfferResponseUserDto() {
        return cargoOfferResponseUserDto;
    }

    public void setCargoOfferResponseUserDto(CargoOfferResponseUserDto cargoOfferResponseUserDto) {
        this.cargoOfferResponseUserDto = cargoOfferResponseUserDto;
    }
}
