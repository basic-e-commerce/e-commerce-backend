package com.example.ecommercebackend.dto.product.coupon;

import com.example.ecommercebackend.dto.product.products.ProductSmallDto;
import com.example.ecommercebackend.dto.user.customer.CustomerProfileDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class CouponAdminResponseDto {
    private Integer id;
    private String code;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private Integer timesUsed;
    private Integer totalUsageLimit; // Genel kullanım limiti (opsiyonel)

    private BigDecimal minOrderAmountLimit;

    private LocalDateTime couponStartDate;
    private LocalDateTime couponEndDate;

    private Boolean isPublic;
    private Boolean isActive;

    public CouponAdminResponseDto(Integer id, String code, String description, String discountType, BigDecimal discountValue, Integer timesUsed, Integer totalUsageLimit, BigDecimal minOrderAmountLimit, LocalDateTime couponStartDate, LocalDateTime couponEndDate, Boolean isPublic, Boolean isActive) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.timesUsed = timesUsed;
        this.totalUsageLimit = totalUsageLimit;
        this.minOrderAmountLimit = minOrderAmountLimit;
        this.couponStartDate = couponStartDate;
        this.couponEndDate = couponEndDate;
        this.isPublic = isPublic;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(Integer timesUsed) {
        this.timesUsed = timesUsed;
    }

    public Integer getTotalUsageLimit() {
        return totalUsageLimit;
    }

    public void setTotalUsageLimit(Integer totalUsageLimit) {
        this.totalUsageLimit = totalUsageLimit;
    }

    public BigDecimal getMinOrderAmountLimit() {
        return minOrderAmountLimit;
    }

    public void setMinOrderAmountLimit(BigDecimal minOrderAmountLimit) {
        this.minOrderAmountLimit = minOrderAmountLimit;
    }

    public LocalDateTime getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(LocalDateTime couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public LocalDateTime getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(LocalDateTime couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
