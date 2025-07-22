package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.entity.product.order.OrderPackage;

public class OrderFilterRequest {
    private String sortBy; // "price", "name" vb.
    private String sortDirection; // "asc" veya "desc"
    private String paymentStatus;
    private OrderPackage.StatusCode statusCode;

    public OrderFilterRequest(String sortBy, String sortDirection, String paymentStatus, OrderPackage.StatusCode statusCode) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.paymentStatus = paymentStatus;
        this.statusCode = statusCode;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderPackage.StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(OrderPackage.StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
