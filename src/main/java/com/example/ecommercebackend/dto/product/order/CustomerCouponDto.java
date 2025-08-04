package com.example.ecommercebackend.dto.product.order;

public class CustomerCouponDto {
    private Integer couponId;
    private String couponCode;

    public CustomerCouponDto(Integer couponId, String couponCode) {
        this.couponId = couponId;
        this.couponCode = couponCode;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
