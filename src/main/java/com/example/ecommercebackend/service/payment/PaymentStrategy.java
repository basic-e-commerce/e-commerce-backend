package com.example.ecommercebackend.service.payment;


import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentInfoDto;
import com.example.ecommercebackend.dto.payment.response.PayCallBackDto;
import com.example.ecommercebackend.dto.payment.response.ProcessCreditCardDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentStrategy {
    ProcessCreditCardDto processCreditCardPayment(Order order,
                                                  PaymentCreditCardRequestDto paymentCreditCardRequestDto,
                                                  String conversationId,
                                                  HttpServletRequest httpServletRequest);
    PayCallBackDto payCallBack(Map<String, String> collections, Payment payment);
    InstallmentInfoDto getBin(String bin, BigDecimal price);
    String refund(String paymentId,BigDecimal refundAmount);
}
