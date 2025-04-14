package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;

public class ProductSellDto {
    private int id;
    private String name;
    private BigDecimal sellPrice;
    private int quantity;
    private String coverImage;

    public ProductSellDto(int id, String name, BigDecimal sellPrice, int quantity, String coverImage) {
        this.id = id;
        this.name = name;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.coverImage = coverImage;
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
}
