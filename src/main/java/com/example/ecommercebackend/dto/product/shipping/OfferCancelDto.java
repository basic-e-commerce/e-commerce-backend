package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferCancelDto {

    @JsonProperty("result")
    private Boolean result;

    @JsonProperty("additionalMessage")
    private String additionalMessage;

    @JsonProperty("data")
    private OfferCancelDataDto data;

    public OfferCancelDto(Boolean result, String additionalMessage, OfferCancelDataDto data) {
        this.result = result;
        this.additionalMessage = additionalMessage;
        this.data = data;
    }

    public OfferCancelDto() {
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

    public OfferCancelDataDto getData() {
        return data;
    }

    public void setData(OfferCancelDataDto data) {
        this.data = data;
    }
}
