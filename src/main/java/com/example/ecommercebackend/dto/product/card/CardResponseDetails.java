package com.example.ecommercebackend.dto.product.card;

import java.math.BigDecimal;

public class CardResponseDetails {
    private int id;
    private String title;
    private String productLinkName;
    private BigDecimal price;
    private BigDecimal comparePrice;
    private String coverImage;
    private int quantity;
    private int productQuantity;

    public CardResponseDetails(int id, String title, String productLinkName, BigDecimal price, BigDecimal comparePrice , String coverImage, int quantity, int productQuantity) {
        this.id = id;
        this.title = title;
        this.productLinkName = productLinkName;
        this.price = price;
        this.comparePrice = comparePrice;
        this.coverImage = coverImage;
        this.quantity = quantity;
        this.productQuantity = productQuantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductLinkName() {
        return productLinkName;
    }

    public void setProductLinkName(String productLinkName) {
        this.productLinkName = productLinkName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
