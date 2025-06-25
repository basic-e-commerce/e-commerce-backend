package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;

public class ProductSellDto {
    private int id;
    private String name;
    private BigDecimal sellPrice;
    private int quantity;
    private String coverImage;
    private String date;
    private String dateTime;

    public ProductSellDto(int id, String name, BigDecimal sellPrice, int quantity, String coverImage, String date, String dateTime) {
        this.id = id;
        this.name = name;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.coverImage = coverImage;
        this.date = date;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
