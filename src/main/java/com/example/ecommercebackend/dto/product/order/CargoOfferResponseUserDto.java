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
    private OfferUserDto cheapest;

    @JsonProperty("fastest")
    private OfferUserDto fastest;

    @JsonProperty("list")
    private List<OfferUserDto> list;

    public CargoOfferResponseUserDto(String length, String width, String height, String weight, OfferUserDto cheapest, OfferUserDto fastest, List<OfferUserDto> list) {
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

    public OfferUserDto getCheapest() {
        return cheapest;
    }

    public void setCheapest(OfferUserDto cheapest) {
        this.cheapest = cheapest;
    }

    public OfferUserDto getFastest() {
        return fastest;
    }

    public void setFastest(OfferUserDto fastest) {
        this.fastest = fastest;
    }

    public List<OfferUserDto> getList() {
        return list;
    }

    public void setList(List<OfferUserDto> list) {
        this.list = list;
    }
}
