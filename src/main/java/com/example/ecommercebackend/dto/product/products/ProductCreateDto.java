package com.example.ecommercebackend.dto.product.products;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

public class ProductCreateDto {
    private String name;
    private BigDecimal salePrice;
    private BigDecimal comparePrice;
    private BigDecimal buyingPrice;
    private Integer quantity;
    private String shortDescription;
    private String productDescription;
    private Set<Integer> categoryIds;
    private String productType;
    private Boolean published;
    private Boolean disableOutOfStock;
    private MultipartFile[] images;
    private MultipartFile coverImage;

    public ProductCreateDto(String name, BigDecimal salePrice, BigDecimal comparePrice, BigDecimal buyingPrice, Integer quantity, String shortDescription, String productDescription, Set<Integer> categoryIds, String productType, Boolean published, Boolean disableOutOfStock, MultipartFile[] images, MultipartFile coverImage) {
        this.name = name;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.buyingPrice = buyingPrice;
        this.quantity = quantity;
        this.shortDescription = shortDescription;
        this.productDescription = productDescription;
        this.categoryIds = categoryIds;
        this.productType = productType;
        this.published = published;
        this.disableOutOfStock = disableOutOfStock;
        this.images = images;
        this.coverImage = coverImage;
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

    public String getProductDescription() {
        return productDescription;
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

    public MultipartFile[] getImages() {
        return images;
    }

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public Boolean getDisableOutOfStock() {
        return disableOutOfStock;
    }

    public void setDisableOutOfStock(Boolean disableOutOfStock) {
        this.disableOutOfStock = disableOutOfStock;
    }
}
