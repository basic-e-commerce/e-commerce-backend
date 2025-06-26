package com.example.ecommercebackend.dto.product.sell;

import com.example.ecommercebackend.dto.file.ImageDetailDto;

public class ProductSmallSellDto {
    private int productId;
    private String productName;
    private ImageDetailDto coverImage;
    private Integer quantity;

    public ProductSmallSellDto(int productId, String productName, ImageDetailDto coverImage, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.coverImage = coverImage;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ImageDetailDto getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageDetailDto coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
