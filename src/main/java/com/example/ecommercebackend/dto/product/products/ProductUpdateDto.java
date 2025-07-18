package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.anotation.NotNullField;

import java.math.BigDecimal;
import java.util.Set;

public class ProductUpdateDto {
    @NotNullField
    private String name;
    @NotNullField
    private BigDecimal salePrice;
    @NotNullField
    private BigDecimal comparePrice;
    @NotNullField
    private BigDecimal buyingPrice;
    @NotNullField
    private Integer quantity;
    @NotNullField
    private String shortDescription;
    @NotNullField
    private String description;
    @NotNullField
    private Set<Integer> categoryIds;
    @NotNullField
    private String productType;
    @NotNullField
    private Boolean published;
    @NotNullField
    private Boolean disableOutOfStock;
    @NotNullField
    private BigDecimal taxRate;
    @NotNullField
    private Integer stockNotification;
    @NotNullField
    private Integer productTemplateId;

    public ProductUpdateDto(String name, BigDecimal salePrice, BigDecimal comparePrice, BigDecimal buyingPrice, Integer quantity, String shortDescription, String description, Set<Integer> categoryIds, String productType, Boolean published, Boolean disableOutOfStock, BigDecimal taxRate, Integer stockNotification, Integer productTemplateId) {
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
        this.taxRate = taxRate;
        this.stockNotification = stockNotification;
        this.productTemplateId = productTemplateId;
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

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getStockNotification() {
        return stockNotification;
    }

    public void setStockNotification(Integer stockNotification) {
        this.stockNotification = stockNotification;
    }

    public Integer getProductTemplateId() {
        return productTemplateId;
    }

    public void setProductTemplateId(Integer productTemplateId) {
        this.productTemplateId = productTemplateId;
    }
}
