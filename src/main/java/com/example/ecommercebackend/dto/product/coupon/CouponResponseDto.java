package com.example.ecommercebackend.dto.product.coupon;

import java.time.LocalDateTime;

public class CouponResponseDto {
    private String code;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CouponResponseDto(String code, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.code = code;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
