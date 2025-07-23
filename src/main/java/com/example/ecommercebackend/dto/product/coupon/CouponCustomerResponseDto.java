package com.example.ecommercebackend.dto.product.coupon;

import java.time.LocalDateTime;

public class CouponCustomerResponseDto {
    private String code;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime lastDateTime;
    private Boolean isActive;


    public CouponCustomerResponseDto(String code, String description, LocalDateTime startDateTime, LocalDateTime lastDateTime, Boolean isActive ) {
        this.code = code;
        this.description = description;
        this.startDateTime = startDateTime;
        this.lastDateTime = lastDateTime;
        this.isActive = isActive;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getLastDateTime() {
        return lastDateTime;
    }

    public void setLastDateTime(LocalDateTime lastDateTime) {
        this.lastDateTime = lastDateTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
