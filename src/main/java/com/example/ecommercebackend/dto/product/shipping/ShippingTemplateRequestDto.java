package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingTemplateRequestDto {
    private Boolean result;
    private ShippingTemplateDto data;

    @JsonCreator
    public ShippingTemplateRequestDto(
            @JsonProperty("result") boolean result,
            @JsonProperty("data") ShippingTemplateDto data) {
        this.result = result;
        this.data = data;
    }

    public ShippingTemplateRequestDto() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ShippingTemplateDto getData() {
        return data;
    }

    public void setData(ShippingTemplateDto data) {
        this.data = data;
    }
}
