package com.example.ecommercebackend.dto.product.products;

import java.math.BigDecimal;

public class ProductSmallDto {
    private int id;
    private String name;
    private String productLinkName;
    private String shortDescription;
    private BigDecimal salePrice;
    private BigDecimal comparePrice;
    private String coverImageUrl;
    private int quantity;

    public ProductSmallDto(int id, String name, String productLinkName, String shortDescription, BigDecimal salePrice, BigDecimal comparePrice, String coverImageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.productLinkName = productLinkName;
        this.shortDescription = shortDescription;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.coverImageUrl = coverImageUrl;
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

    public String getProductLinkName() {
        return productLinkName;
    }

    public void setProductLinkName(String productLinkName) {
        this.productLinkName = productLinkName;
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

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
