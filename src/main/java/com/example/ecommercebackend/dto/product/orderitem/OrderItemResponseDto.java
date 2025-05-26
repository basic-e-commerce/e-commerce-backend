package com.example.ecommercebackend.dto.product.orderitem;

public class OrderItemResponseDto {
    private int productId;
    private String productName;
    private int quantity;
    private String coverImage;

    public OrderItemResponseDto(int productId, String productName, int quantity, String coverImage) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.coverImage = coverImage;
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
}
