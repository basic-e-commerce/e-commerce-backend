package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoOfferResponseDto {
    @JsonProperty("result")
    private String result;
    @JsonProperty("additionalMessage")
    private String additionalMessage;

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private CargoOfferData data;

    public CargoOfferResponseDto(String result, String additionalMessage, boolean success, String code, String message, CargoOfferData data) {
        this.result = result;
        this.additionalMessage = additionalMessage;
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CargoOfferResponseDto() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public CargoOfferData getData() {
        return data;
    }

    public void setData(CargoOfferData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
