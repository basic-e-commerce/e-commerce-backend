package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OffersDto {
    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

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

    @JsonProperty("percentageCompleted")
    private int percentageCompleted;

    @JsonProperty("totalOffersRequested")
    private int totalOffersRequested;

    @JsonProperty("totalOffersCompleted")
    private int totalOffersCompleted;

    @JsonProperty("allowOfferFallback")
    private boolean allowOfferFallback;

    public OffersDto(String createdAt, String updatedAt, String length, String width, String height, String weight, OfferDto cheapest, OfferDto fastest, List<OfferDto> list, int percentageCompleted, int totalOffersRequested, int totalOffersCompleted, boolean allowOfferFallback) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.cheapest = cheapest;
        this.fastest = fastest;
        this.list = list;
        this.percentageCompleted = percentageCompleted;
        this.totalOffersRequested = totalOffersRequested;
        this.totalOffersCompleted = totalOffersCompleted;
        this.allowOfferFallback = allowOfferFallback;
    }

    public OffersDto() {
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public int getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(int percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public int getTotalOffersRequested() {
        return totalOffersRequested;
    }

    public void setTotalOffersRequested(int totalOffersRequested) {
        this.totalOffersRequested = totalOffersRequested;
    }

    public int getTotalOffersCompleted() {
        return totalOffersCompleted;
    }

    public void setTotalOffersCompleted(int totalOffersCompleted) {
        this.totalOffersCompleted = totalOffersCompleted;
    }

    public boolean isAllowOfferFallback() {
        return allowOfferFallback;
    }

    public void setAllowOfferFallback(boolean allowOfferFallback) {
        this.allowOfferFallback = allowOfferFallback;
    }
}
