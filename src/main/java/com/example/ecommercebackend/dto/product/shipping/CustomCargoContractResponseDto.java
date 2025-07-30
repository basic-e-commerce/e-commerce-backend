package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomCargoContractResponseDto {
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("data")
    private CustomCargoContractResponseDataDto data;

    public CustomCargoContractResponseDto(Boolean result, CustomCargoContractResponseDataDto data) {
        this.result = result;
        this.data = data;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public CustomCargoContractResponseDataDto getData() {
        return data;
    }

    public void setData(CustomCargoContractResponseDataDto data) {
        this.data = data;
    }
}
