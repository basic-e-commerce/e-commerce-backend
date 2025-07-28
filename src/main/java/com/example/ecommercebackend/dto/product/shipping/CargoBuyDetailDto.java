package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoBuyDetailDto {
    @JsonProperty("result")
    private boolean result;

    @JsonProperty("additionalMessage")
    private String additionalMessage;

    @JsonProperty("data")
    private CargoBuyDetailDataDto data;

    public CargoBuyDetailDto(boolean result, String additionalMessage, CargoBuyDetailDataDto data) {
        this.result = result;
        this.additionalMessage = additionalMessage;
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

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public CargoBuyDetailDataDto getData() {
        return data;
    }

    public void setData(CargoBuyDetailDataDto data) {
        this.data = data;
    }
}
