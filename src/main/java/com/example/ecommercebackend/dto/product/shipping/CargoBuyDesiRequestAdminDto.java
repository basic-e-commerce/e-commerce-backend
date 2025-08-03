package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CargoBuyDesiRequestAdminDto {
    @JsonProperty("orderCode")
    private String orderCode;

    List<CargoBuyDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto;

    public CargoBuyDesiRequestAdminDto(String orderCode, List<CargoBuyDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto) {
        this.orderCode = orderCode;
        this.cargoBuyDesiRequestAdminDataDto = cargoBuyDesiRequestAdminDataDto;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<CargoBuyDesiRequestAdminDataDto> getCargoBuyDesiRequestAdminDataDto() {
        return cargoBuyDesiRequestAdminDataDto;
    }

    public void setCargoBuyDesiRequestAdminDataDto(List<CargoBuyDesiRequestAdminDataDto> cargoBuyDesiRequestAdminDataDto) {
        this.cargoBuyDesiRequestAdminDataDto = cargoBuyDesiRequestAdminDataDto;
    }
}
