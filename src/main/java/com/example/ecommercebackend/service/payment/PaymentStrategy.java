package com.example.ecommercebackend.service.payment;


import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentInfoDto;
import com.example.ecommercebackend.dto.payment.response.PayCallBackDto;
import com.example.ecommercebackend.dto.payment.response.PaymentComplateDto;
import com.example.ecommercebackend.dto.payment.response.ProcessCreditCardDto;
import com.example.ecommercebackend.entity.product.order.Order;
import com.iyzipay.model.Cancel;
import com.iyzipay.model.Refund;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface PaymentStrategy {
    ProcessCreditCardDto processCreditCardPayment(Order order,
                                                  PaymentCreditCardRequestDto paymentCreditCardRequestDto,
                                                  String conversationId,
                                                  HttpServletRequest httpServletRequest);
    PayCallBackDto payCallBack(PaymentComplateDto paymentComplateDto);
    InstallmentInfoDto getBin(String bin, BigDecimal price);
    Refund refund(String paymentId, BigDecimal refundAmount);
    Cancel cancel(String paymentId);
}
