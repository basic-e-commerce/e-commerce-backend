package com.example.ecommercebackend.controller.payment;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentInfoDto;
import com.example.ecommercebackend.dto.product.order.OrderCreateDto;
import com.example.ecommercebackend.service.payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> processCreditCardPayment(@RequestBody(required = false) OrderCreateDto orderCreateDto,
                                                           HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(paymentService.processCreditCardPayment(orderCreateDto,httpServletRequest), HttpStatus.OK);
    }


    @PostMapping("/payCallBack")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public void payCallBack(@RequestParam Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        paymentService.payCallBack(collections,httpServletResponse);
    }

    @GetMapping("/bin")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<InstallmentInfoDto> getBin(@RequestParam String bin, @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(paymentService.getBin(bin,amount),HttpStatus.OK);
    }
}
