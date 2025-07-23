package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.shipping.OfferDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CargoOfferResponseUserDto {

    @JsonProperty("length")
    private String length;

    @JsonProperty("width")
    private String width;

    @JsonProperty("height")
    private String height;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("cheapest")
    private OfferDto cheapest;

    @JsonProperty("fastest")
    private OfferDto fastest;

    @JsonProperty("list")
    private List<OfferDto> list;

    public CargoOfferResponseUserDto(String length, String width, String height, String weight, OfferDto cheapest, OfferDto fastest, List<OfferDto> list) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.cheapest = cheapest;
        this.fastest = fastest;
        this.list = list;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public OfferDto getCheapest() {
        return cheapest;
    }

    public void setCheapest(OfferDto cheapest) {
        this.cheapest = cheapest;
    }

    public OfferDto getFastest() {
        return fastest;
    }

    public void setFastest(OfferDto fastest) {
        this.fastest = fastest;
    }

    public List<OfferDto> getList() {
        return list;
    }

    public void setList(List<OfferDto> list) {
        this.list = list;
    }
}
