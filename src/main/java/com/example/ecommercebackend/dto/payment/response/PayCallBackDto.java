package com.example.ecommercebackend.dto.payment.response;

import org.apache.tika.sax.Link;

import java.util.List;

public class PayCallBackDto {
    private String paymentId;
    private String conversationId;
    private String status;
    private List<OrderItemTansactionId> orderItemTansactionIds;

    public PayCallBackDto(String paymentId, String conversationId, String status, List<OrderItemTansactionId> orderItemTansactionIds) {
        this.paymentId = paymentId;
        this.conversationId = conversationId;
        this.status = status;
        this.orderItemTansactionIds = orderItemTansactionIds;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemTansactionId> getOrderItemTansactionIds() {
        return orderItemTansactionIds;
    }

    public void setOrderItemTansactionIds(List<OrderItemTansactionId> orderItemTansactionIds) {
        this.orderItemTansactionIds = orderItemTansactionIds;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
