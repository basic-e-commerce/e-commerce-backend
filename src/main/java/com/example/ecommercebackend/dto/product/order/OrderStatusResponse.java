package com.example.ecommercebackend.dto.product.order;

import java.util.List;

public class OrderStatusResponse {
    private Integer id;
    private String status;
    private List<OrderPackageResponseDto> orderPackages;
    private List<OrderPackageResponseDto> refundOrderPackages;
    private String color;
    private String createdAt;

    public OrderStatusResponse(Integer id, String status, List<OrderPackageResponseDto> orderPackages, List<OrderPackageResponseDto> refundOrderPackages, String color, String createdAt) {
        this.id = id;
        this.status = status;
        this.orderPackages = orderPackages;
        this.refundOrderPackages = refundOrderPackages;
        this.color = color;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderPackageResponseDto> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackageResponseDto> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderPackageResponseDto> getRefundOrderPackages() {
        return refundOrderPackages;
    }

    public void setRefundOrderPackages(List<OrderPackageResponseDto> refundOrderPackages) {
        this.refundOrderPackages = refundOrderPackages;
    }
}
