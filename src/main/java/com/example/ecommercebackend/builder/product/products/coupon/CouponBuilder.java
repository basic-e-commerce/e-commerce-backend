package com.example.ecommercebackend.builder.product.products.coupon;

import com.example.ecommercebackend.dto.product.products.coupon.CouponCreateDto;
import com.example.ecommercebackend.entity.product.products.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponBuilder {
    public Coupon couponCreateDtoToCoupon(CouponCreateDto couponCreateDto){
        return new Coupon(
                couponCreateDto.getCode(),
                couponCreateDto.getDiscountValue(),
                Coupon.DiscountType.valueOf(couponCreateDto.getDiscountType()),
                couponCreateDto.getMaxUsage(),
                couponCreateDto.getMinOrderAmountLimit(),
                couponCreateDto.getStartDate(),
                couponCreateDto.getEndDate()
        );
    }
}
