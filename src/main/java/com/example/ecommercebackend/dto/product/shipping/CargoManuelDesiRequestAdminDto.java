package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CargoManuelDesiRequestAdminDto {
    @JsonProperty("orderCode")
    private String orderCode;

    List<CargoManuelDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto;

    public CargoManuelDesiRequestAdminDto(String orderCode, List<CargoManuelDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto) {
        this.orderCode = orderCode;
        this.cargoBuyDesiRequestAdminDataDto = cargoBuyDesiRequestAdminDataDto;
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
}
