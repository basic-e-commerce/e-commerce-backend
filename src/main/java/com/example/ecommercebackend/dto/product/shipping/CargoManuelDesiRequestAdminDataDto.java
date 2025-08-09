package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemRequestDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class CargoManuelDesiRequestAdminDataDto {

    @JsonProperty("cargoCompany")
    private OrderPackage.CargoCompany cargoCompany;

    private List<OrderItemRequestDto> orderItems;

    private BigDecimal cargoFee;

    public CargoManuelDesiRequestAdminDataDto(OrderPackage.CargoCompany cargoCompany, List<OrderItemRequestDto> orderItems, BigDecimal cargoFee) {
        this.cargoCompany = cargoCompany;
        this.orderItems = orderItems;
        this.cargoFee = cargoFee;
    }

    public OrderPackage.CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
    }

    public List<OrderItemRequestDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequestDto> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getCargoFee() {
        return cargoFee;
    }

    public void setCargoFee(BigDecimal cargoFee) {
        this.cargoFee = cargoFee;
    }
}
