package com.example.ecommercebackend.controller.payment;

import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentInfoDto;
import com.example.ecommercebackend.service.payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> processCreditCardPayment(@RequestBody PaymentCreditCardRequestDto paymentCreditCardRequestDto,
                                                           HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(paymentService.processCreditCardPayment(paymentCreditCardRequestDto,httpServletRequest), HttpStatus.OK);
    }


    @PostMapping("/payCallBack")
    public void payCallBack(@RequestParam Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        paymentService.payCallBack(collections,httpServletResponse);
    }

    @GetMapping("/bin")
    public ResponseEntity<InstallmentInfoDto> getBin(@RequestParam String bin, @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(paymentService.getBin(bin,amount),HttpStatus.OK);
    }
}
