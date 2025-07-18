package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.shipping.CargoOfferResponseDto;

import java.util.List;
import java.util.Set;

public class CargoOfferResponsesDto {
    List<CargoOfferResponseDto> cargoOfferResponseDtos;
    Set<Integer> orderPackageIds;

    public CargoOfferResponsesDto(List<CargoOfferResponseDto> cargoOfferResponseDtos, Set<Integer> orderPackageIds) {
        this.cargoOfferResponseDtos = cargoOfferResponseDtos;
        this.orderPackageIds = orderPackageIds;
    }

    public List<CargoOfferResponseDto> getCargoOfferResponseDtos() {
        return cargoOfferResponseDtos;
    }

    public void setCargoOfferResponseDtos(List<CargoOfferResponseDto> cargoOfferResponseDtos) {
        this.cargoOfferResponseDtos = cargoOfferResponseDtos;
    }

    public Set<Integer> getOrderPackageIds() {
        return orderPackageIds;
    }

    public void setOrderPackageIds(Set<Integer> orderPackageIds) {
        this.orderPackageIds = orderPackageIds;
    }
}
