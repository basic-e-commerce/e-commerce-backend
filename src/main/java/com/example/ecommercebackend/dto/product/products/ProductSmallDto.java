package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.dto.file.ImageDetailDto;

import java.math.BigDecimal;

public class ProductSmallDto {
    private int id;
    private String name;
    private String linkName;
    private String shortDescription;
    private BigDecimal salePrice;
    private BigDecimal comparePrice;
    private ImageDetailDto coverImage;
    private int quantity;

    public ProductSmallDto(int id, String name, String linkName, String shortDescription, BigDecimal salePrice, BigDecimal comparePrice, ImageDetailDto coverImage, int quantity) {
        this.id = id;
        this.name = name;
        this.linkName = linkName;
        this.shortDescription = shortDescription;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.coverImage = coverImage;

        this.quantity = quantity;
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

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(BigDecimal comparePrice) {
        this.comparePrice = comparePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ImageDetailDto getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageDetailDto coverImage) {
        this.coverImage = coverImage;
    }
}
