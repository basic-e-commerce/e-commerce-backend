package com.example.ecommercebackend.dto.product.products;

import java.math.BigDecimal;
import java.util.Set;

public class ProductUpdateDto {
    private String name;
    private BigDecimal salePrice;
    private BigDecimal comparePrice;
    private BigDecimal buyingPrice;
    private Integer quantity;
    private String shortDescription;
    private String description;
    private Set<Integer> categoryIds;
    private String productType;
    private Boolean published;
    private Boolean disableOutOfStock;

    public ProductUpdateDto(String name, BigDecimal salePrice, BigDecimal comparePrice, BigDecimal buyingPrice, Integer quantity, String shortDescription, String description, Set<Integer> categoryIds, String productType, Boolean published, Boolean disableOutOfStock) {
        this.name = name;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.buyingPrice = buyingPrice;
        this.quantity = quantity;
        this.shortDescription = shortDescription;
        this.description = description;
        this.categoryIds = categoryIds;
        this.productType = productType;
        this.published = published;
        this.disableOutOfStock = disableOutOfStock;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    public String getProductType() {
        return productType;
    }

    public Boolean getPublished() {
        return published;
    }

    public Boolean getDisableOutOfStock() {
        return disableOutOfStock;
    }
}
