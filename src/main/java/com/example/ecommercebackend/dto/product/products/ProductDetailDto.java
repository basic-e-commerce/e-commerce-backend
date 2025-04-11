package com.example.ecommercebackend.dto.product.products;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetailDto {
    private int id;
    private String name;
    private String productLinkName;
    private String shortDescription;
    private String description;
    private BigDecimal salePrice;
    private BigDecimal comparePrice;
    private String coverImageUrl;
    private List<String> productImageUrls;
    private int quantity;

    public ProductDetailDto(int id, String name, String productLinkName, String shortDescription, String description, BigDecimal salePrice, BigDecimal comparePrice, String coverImageUrl, List<String> productImageUrls, int quantity) {
        this.id = id;
        this.name = name;
        this.productLinkName = productLinkName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.coverImageUrl = coverImageUrl;
        this.productImageUrls = productImageUrls;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getProductImageUrls() {
        return productImageUrls;
    }

    public void setProductImageUrls(List<String> productImageUrls) {
        this.productImageUrls = productImageUrls;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
