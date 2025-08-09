package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemRefundDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;

import java.util.List;

public class OrderPackageUpdateDto {
    private OrderPackage.OrderPackageStatusCode orderPackageStatusCode;
    private List<OrderItemRefundDto> orderItemRefundDtos;
    private String location;
    private String orderCode;

    public OrderPackageUpdateDto(OrderPackage.OrderPackageStatusCode orderPackageStatusCode, List<OrderItemRefundDto> orderItemRefundDtos, String location, String orderCode) {
        this.orderPackageStatusCode = orderPackageStatusCode;
        this.orderItemRefundDtos = orderItemRefundDtos;
        this.location = location;
        this.orderCode = orderCode;
    }

    public OrderPackage.OrderPackageStatusCode getOrderPackageStatusCode() {
        return orderPackageStatusCode;
    }

    public void setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode orderPackageStatusCode) {
        this.orderPackageStatusCode = orderPackageStatusCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<OrderItemRefundDto> getOrderItemRefundDtos() {
        return orderItemRefundDtos;
    }

    public void setOrderItemRefundDtos(List<OrderItemRefundDto> orderItemRefundDtos) {
        this.orderItemRefundDtos = orderItemRefundDtos;
    }
}
