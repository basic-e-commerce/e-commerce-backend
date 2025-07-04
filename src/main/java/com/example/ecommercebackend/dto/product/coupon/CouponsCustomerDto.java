package com.example.ecommercebackend.dto.product.coupon;

import java.time.LocalDateTime;

public class CouponsCustomerDto {
    private Integer customerId;
    private String name;
    private String lastName;
    private Boolean isUsed;
    private LocalDateTime usedTime;

    public CouponsCustomerDto(Integer customerId, String name, String lastName, Boolean isUsed, LocalDateTime usedTime) {
        this.customerId = customerId;
        this.name = name;
        this.lastName = lastName;
        this.isUsed = isUsed;
        this.usedTime = usedTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
