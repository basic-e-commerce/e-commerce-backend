package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoCancelDetailDto {
    @JsonProperty("result")
    private boolean result;

    @JsonProperty("additionalMessage")
    private String additionalMessage;

    @JsonProperty("data")
    CargoCancelDetailDataDto cargoCancelDetailDataDto;

    public CargoCancelDetailDto(boolean result, String additionalMessage, CargoCancelDetailDataDto cargoCancelDetailDataDto) {
        this.result = result;
        this.additionalMessage = additionalMessage;
        this.cargoCancelDetailDataDto = cargoCancelDetailDataDto;
    }

    public CargoCancelDetailDto() {
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

    public CargoCancelDetailDataDto getCargoCancelDetailDataDto() {
        return cargoCancelDetailDataDto;
    }

    public void setCargoCancelDetailDataDto(CargoCancelDetailDataDto cargoCancelDetailDataDto) {
        this.cargoCancelDetailDataDto = cargoCancelDetailDataDto;
    }
}
