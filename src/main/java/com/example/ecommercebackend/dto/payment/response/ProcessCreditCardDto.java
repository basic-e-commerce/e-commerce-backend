package com.example.ecommercebackend.dto.payment.response;

public class ProcessCreditCardDto {
    private String conversationId;
    private String paymentId;
    private long orderId;
    private String getHtmlContent;
    private String status;

    public ProcessCreditCardDto(String conversationId, String paymentId, long orderId, String getHtmlContent, String status) {
        this.conversationId = conversationId;
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.getHtmlContent = getHtmlContent;
        this.status = status;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getGetHtmlContent() {
        return getHtmlContent;
    }

    public void setGetHtmlContent(String getHtmlContent) {
        this.getHtmlContent = getHtmlContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
