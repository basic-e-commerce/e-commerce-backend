package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CargoManuelDesiRequestAdminDto {
    @JsonProperty("orderCode")
    private String orderCode;

    List<CargoManuelDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto;
    private String cargoCode;

    public CargoManuelDesiRequestAdminDto(String orderCode, List<CargoManuelDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto, String cargoCode) {
        this.orderCode = orderCode;
        this.cargoBuyDesiRequestAdminDataDto = cargoBuyDesiRequestAdminDataDto;
        this.cargoCode = cargoCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<CargoManuelDesiRequestAdminDataDto> getCargoBuyDesiRequestAdminDataDto() {
        return cargoBuyDesiRequestAdminDataDto;
    }

    public void setCargoBuyDesiRequestAdminDataDto(List<CargoManuelDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto) {
        this.cargoBuyDesiRequestAdminDataDto = cargoBuyDesiRequestAdminDataDto;
    }

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }
}
