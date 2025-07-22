package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.invoice.InvoiceResponseDto;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.entity.product.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailDto {
    private int id;
    private String orderCode;
    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private BigDecimal customerPrice;
    private AddressOrderDetailDto address;
    private List<OrderItemResponseDto> orderItemResponseDtos;
    private int installment;
    private InvoiceResponseDto invoiceResponseDto;
    private OrderStatusResponse orderStatusResponse;

    public OrderDetailDto(int id, String orderCode, String firstName, String lastName, BigDecimal totalPrice, BigDecimal customerPrice, AddressOrderDetailDto address, List<OrderItemResponseDto> orderItemResponseDtos, int installment, OrderStatusResponse orderStatusResponse) {
        this.id = id;
        this.orderCode = orderCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPrice = totalPrice;
        this.customerPrice = customerPrice;
        this.address = address;
        this.orderItemResponseDtos = orderItemResponseDtos;
        this.installment = installment;
        this.orderStatusResponse = orderStatusResponse;
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

    public InvoiceResponseDto getInvoiceResponseDto() {
        return invoiceResponseDto;
    }

    public void setInvoiceResponseDto(InvoiceResponseDto invoiceResponseDto) {
        this.invoiceResponseDto = invoiceResponseDto;
    }

    public BigDecimal getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(BigDecimal customerPrice) {
        this.customerPrice = customerPrice;
    }

    public OrderStatusResponse getOrderStatusResponse() {
        return orderStatusResponse;
    }

    public void setOrderStatusResponse(OrderStatusResponse orderStatusResponse) {
        this.orderStatusResponse = orderStatusResponse;
    }
}
