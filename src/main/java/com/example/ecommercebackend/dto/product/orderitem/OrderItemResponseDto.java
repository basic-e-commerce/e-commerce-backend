package com.example.ecommercebackend.dto.product.orderitem;

import java.math.BigDecimal;

public class OrderItemResponseDto {
    private int productId;
    private String productName;
    private int quantity;
    private String coverImage;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer orderItemId;

    public OrderItemResponseDto(int productId, String productName, int quantity, String coverImage, BigDecimal price, BigDecimal discountPrice, Integer orderItemId) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.coverImage = coverImage;
        this.price = price;
        this.discountPrice = discountPrice;
        this.orderItemId = orderItemId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }
}
