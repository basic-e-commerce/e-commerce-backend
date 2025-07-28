package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoBuyDetailDto {
    @JsonProperty("result")
    private boolean result;

    @JsonProperty("additionalMessage")
    private String message;

    @JsonProperty("data")
    private CargoBuyDetailDataDto data;

    public CargoBuyDetailDto(boolean result, String message, CargoBuyDetailDataDto data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public CargoBuyDetailDto() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CargoBuyDetailDataDto getData() {
        return data;
    }

    public void setData(CargoBuyDetailDataDto data) {
        this.data = data;
    }
}
