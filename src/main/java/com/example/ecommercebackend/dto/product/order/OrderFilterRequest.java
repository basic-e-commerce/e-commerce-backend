package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.entity.product.order.OrderStatus;

public class OrderFilterRequest {
    private String sortBy; // "price", "name" vb.
    private String sortDirection; // "asc" veya "desc"
    private Payment.PaymentStatus paymentStatus;
    private OrderPackage.StatusCode statusCode;
    private OrderStatus.Status orderStatus;

    public OrderFilterRequest(String sortBy, String sortDirection, Payment.PaymentStatus paymentStatus, OrderPackage.StatusCode statusCode, OrderStatus.Status orderStatus) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.paymentStatus = paymentStatus;
        this.statusCode = statusCode;
        this.orderStatus = orderStatus;
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

    public Payment.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Payment.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderPackage.StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(OrderPackage.StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public OrderStatus.Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus.Status orderStatus) {
        this.orderStatus = orderStatus;
    }
}
