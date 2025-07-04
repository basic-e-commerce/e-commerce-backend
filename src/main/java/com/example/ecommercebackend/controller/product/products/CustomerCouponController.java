package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;
import com.example.ecommercebackend.service.product.products.CustomerCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-coupon")
public class CustomerCouponController {
    private final CustomerCouponService customerCouponService;

    public CustomerCouponController(CustomerCouponService customerCouponService) {
        this.customerCouponService = customerCouponService;
    }

    @GetMapping("/has-customer")
    public ResponseEntity<List<CouponResponseDto>> hasCustomer() {
        return new ResponseEntity<>(customerCouponService.customerHasCoupon(), HttpStatus.OK);
    }
}
