package com.example.ecommercebackend.dto.product.card;

import java.math.BigDecimal;

public class CardResponseDetails {
    private int id;
    private String title;
    private BigDecimal price;
    private BigDecimal comparePrice;
    private String coverImage;

    public CardResponseDetails(int id, String title, BigDecimal price, BigDecimal comparePrice, String coverImage) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.comparePrice = comparePrice;
        this.coverImage = coverImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(BigDecimal comparePrice) {
        this.comparePrice = comparePrice;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
