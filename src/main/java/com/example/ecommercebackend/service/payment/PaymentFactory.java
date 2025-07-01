package com.example.ecommercebackend.service.payment;


import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.payment.paymentprovider.IyzicoPayment;

public class PaymentFactory {

    public static PaymentStrategy getPaymentMethod(String paymentMethod) {
        return switch (paymentMethod.toUpperCase()) {
            case "IYZICO" -> new IyzicoPayment();
            default -> throw new BadRequestException("Unknown payment method: " + paymentMethod);
        };
    }
}
