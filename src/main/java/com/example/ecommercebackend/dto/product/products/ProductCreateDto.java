package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.anotation.NotNullField;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

public class ProductCreateDto {
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
    private MultipartFile[] productImages;
    @NotNullField
    private MultipartFile coverImage;

    public ProductCreateDto(String name, BigDecimal salePrice, BigDecimal comparePrice, BigDecimal buyingPrice, Integer quantity, String shortDescription, String description, Set<Integer> categoryIds, String productType, Boolean published, Boolean disableOutOfStock, MultipartFile[] productImages, MultipartFile coverImage) {
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
        this.productImages = productImages;
        this.coverImage = coverImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getDisableOutOfStock() {
        return disableOutOfStock;
    }

    public void setDisableOutOfStock(Boolean disableOutOfStock) {
        this.disableOutOfStock = disableOutOfStock;
    }

    public MultipartFile[] getProductImages() {
        return productImages;
    }

    public void setProductImages(MultipartFile[] productImages) {
        this.productImages = productImages;
    }

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}
