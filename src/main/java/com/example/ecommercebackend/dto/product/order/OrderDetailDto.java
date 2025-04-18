package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailDto {
    private int id;
    private String orderCode;
    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private AddressOrderDetailDto address;
    private List<OrderItemResponseDto> orderItemResponseDtos;
    private int installment;

    public OrderDetailDto(int id, String orderCode, String firstName, String lastName, BigDecimal totalPrice, AddressOrderDetailDto address, List<OrderItemResponseDto> orderItemResponseDtos, int installment) {
        this.id = id;
        this.orderCode = orderCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPrice = totalPrice;
        this.address = address;
        this.orderItemResponseDtos = orderItemResponseDtos;
        this.installment = installment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public AddressOrderDetailDto getAddress() {
        return address;
    }

    public void setAddress(AddressOrderDetailDto address) {
        this.address = address;
    }

    public List<OrderItemResponseDto> getOrderItemResponseDtos() {
        return orderItemResponseDtos;
    }

    public void setOrderItemResponseDtos(List<OrderItemResponseDto> orderItemResponseDtos) {
        this.orderItemResponseDtos = orderItemResponseDtos;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }
}
