package com.example.ecommercebackend.dto.payment.refund;

import com.example.ecommercebackend.dto.product.orderitem.OrderItemRefundDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public class RefundCreateDto {
    private List<OrderItemRefundDto> orderItemRefundDtos;
    private BigDecimal refundAmount;
    private String orderCode;

    public RefundCreateDto(List<OrderItemRefundDto> orderItemRefundDtos, BigDecimal refundAmount, String orderCode) {
        this.orderItemRefundDtos = orderItemRefundDtos;
        this.refundAmount = refundAmount;
        this.orderCode = orderCode;
    }

    public List<OrderItemRefundDto> getOrderItemRefundDtos() {
        return orderItemRefundDtos;
    }

    public void setOrderItemRefundDtos(List<OrderItemRefundDto> orderItemRefundDtos) {
        this.orderItemRefundDtos = orderItemRefundDtos;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
