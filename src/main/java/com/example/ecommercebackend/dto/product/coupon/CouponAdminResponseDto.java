package com.example.ecommercebackend.dto.product.coupon;

import com.example.ecommercebackend.dto.product.products.ProductSmallDto;
import com.example.ecommercebackend.dto.user.customer.CustomerProfileDto;

import java.math.BigDecimal;
import java.time.Instant;
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

    private Instant couponStartDate;
    private Instant couponEndDate;

    private Boolean isPublic;
    private Boolean isActive;
    private List<ProductSmallDto> products;
    private List<CustomerProfileDto> customerProfileDtos;

    public CouponAdminResponseDto(Integer id, String code, String description, String discountType, BigDecimal discountValue, Integer timesUsed, Integer totalUsageLimit, BigDecimal minOrderAmountLimit, Instant couponStartDate, Instant couponEndDate, Boolean isPublic, Boolean isActive, List<ProductSmallDto> products, List<CustomerProfileDto> customerProfileDtos) {
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
        this.products = products;
        this.customerProfileDtos = customerProfileDtos;
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

    public Instant getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(Instant couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public Instant getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(Instant couponEndDate) {
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

    public List<ProductSmallDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSmallDto> products) {
        this.products = products;
    }

    public List<CustomerProfileDto> getCustomerProfileDtos() {
        return customerProfileDtos;
    }

    public void setCustomerProfileDtos(List<CustomerProfileDto> customerProfileDtos) {
        this.customerProfileDtos = customerProfileDtos;
    }
}
