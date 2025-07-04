package com.example.ecommercebackend.dto.product.coupon;

import java.time.LocalDateTime;

public class CouponResponseDto {
    private Boolean isUsed;
    private LocalDateTime usedTime;

    public CouponResponseDto(Boolean isUsed, LocalDateTime usedTime) {
        this.isUsed = isUsed;
        this.usedTime = usedTime;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime) {
        this.usedTime = usedTime;
    }
}
