package com.example.ecommercebackend.service.payment;


import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.payment.paymentprovider.IyzicoPayment;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PaymentFactory {
    private final ApplicationContext applicationContext;

    public PaymentFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public PaymentStrategy getPaymentMethod(String paymentMethod) {
        return switch (paymentMethod.toUpperCase()) {
            case "IYZICO" -> applicationContext.getBean(IyzicoPayment.class);
            default -> throw new BadRequestException("Unknown payment method: " + paymentMethod);
        };
    }
}
