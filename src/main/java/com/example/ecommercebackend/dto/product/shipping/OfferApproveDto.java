package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferApproveDto {
    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private OfferDetailDto data;

    public OfferApproveDto(Boolean result, String message, OfferDetailDto data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public OfferApproveDto() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OfferDetailDto getData() {
        return data;
    }

    public void setData(OfferDetailDto data) {
        this.data = data;
    }
}
