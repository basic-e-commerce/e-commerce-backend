package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemRefundDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;

import java.util.List;

public class OrderPackageUpdateDto {
    private OrderPackage.OrderPackageStatusCode orderPackageStatusCode;
    private String orderCode;

    public OrderPackageUpdateDto(OrderPackage.OrderPackageStatusCode orderPackageStatusCode,String orderCode) {
        this.orderPackageStatusCode = orderPackageStatusCode;
        this.orderCode = orderCode;
    }

    public OrderPackage.OrderPackageStatusCode getOrderPackageStatusCode() {
        return orderPackageStatusCode;
    }

    public void setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode orderPackageStatusCode) {
        this.orderPackageStatusCode = orderPackageStatusCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


}
