package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoOfferRequestItem {
    @JsonProperty("title")
    private String title;

    @JsonProperty("quantity")
    private Integer quantity;

    public CargoOfferRequestItem(String title, Integer quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
