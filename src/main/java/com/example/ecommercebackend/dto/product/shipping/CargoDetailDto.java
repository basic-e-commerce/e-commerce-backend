package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoDetailDto {
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("additionalMessage")
    private String additionalMessage;
    @JsonProperty("data")
    private CargoDetailDataDto data;

    public CargoDetailDto(Boolean result, String additionalMessage, CargoDetailDataDto data) {
        this.result = result;
        this.additionalMessage = additionalMessage;
        this.data = data;
    }

    public CargoDetailDto() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public CargoDetailDataDto getData() {
        return data;
    }

    public void setData(CargoDetailDataDto data) {
        this.data = data;
    }
}
