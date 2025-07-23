package com.example.ecommercebackend.builder.product.products.coupon;

import com.example.ecommercebackend.dto.product.coupon.CouponAdminResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;
import com.example.ecommercebackend.dto.product.products.coupon.CouponCreateDto;
import com.example.ecommercebackend.entity.product.products.Coupon;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

@Component
public class CouponBuilder {
    public CouponAdminResponseDto couponToCouponAdminResponseDto(Coupon coupon) {
        return new CouponAdminResponseDto(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountType().name(),
                coupon.getDiscountValue(),
                coupon.getTimesUsed(),
                coupon.getTotalUsageLimit(),
                coupon.getMinOrderAmountLimit(),
                coupon.getCouponStartDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
                coupon.getCouponEndDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
                coupon.getActive()
        );
    }

}
