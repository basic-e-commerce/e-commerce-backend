package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.product.coupon.CouponAdminResponseDto;
import com.example.ecommercebackend.dto.product.products.coupon.CouponCreateDto;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.service.product.products.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponAdminResponseDto> create(@RequestBody CouponCreateDto couponCreateDto){
        return new ResponseEntity<>(couponService.createCoupon(couponCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CouponAdminResponseDto>> getAll(@RequestParam(required = false,defaultValue = "0") Integer page,@RequestParam(required = false,defaultValue = "10") Integer size){
        return new ResponseEntity<>(couponService.getAll(page,size),HttpStatus.OK);
    }

    @PutMapping("/active")
    public ResponseEntity<CouponAdminResponseDto> activate(@RequestParam(required = false) String code,@RequestParam(required = false) Boolean active){
        return new ResponseEntity<>(couponService.changeActive(code,active),HttpStatus.OK);
    }
}
