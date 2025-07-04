package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.product.coupon.CouponCustomerResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponsCustomerDto;
import com.example.ecommercebackend.dto.user.customer.CustomerProfileDto;
import com.example.ecommercebackend.service.product.products.CustomerCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-coupon")
public class CustomerCouponController {
    private final CustomerCouponService customerCouponService;

    public CustomerCouponController(CustomerCouponService customerCouponService) {
        this.customerCouponService = customerCouponService;
    }

    @GetMapping("/has-customer")
    public ResponseEntity<List<CouponCustomerResponseDto>> hasCustomer() {
        return new ResponseEntity<>(customerCouponService.customerHasCoupon(), HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CouponResponseDto>> customerhasCoupons(@RequestParam(required = false) Integer customerId,
                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<>(customerCouponService.customerhasCoupons(customerId,page,size),HttpStatus.OK);
    }

    @GetMapping("/coupon")
    public ResponseEntity<List<CouponsCustomerDto>> couponsHasCustomer(@RequestParam(required = false) Integer couponId,
                                                                       @RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<>(customerCouponService.couponsHasCustomer(couponId,page,size),HttpStatus.OK);
    }


}
