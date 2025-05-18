package com.example.ecommercebackend.dto.product.products.coupon;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class CouponCreateDto {
    private String code;
    private BigDecimal discountValue;  // eğer yüzdelikse 100 den büyük olamaz. diğeriyse sayısal değer verilir.
    private String discountType;
    private int maxUsage;
    private BigDecimal minOrderAmountLimit;
    private Instant startDate;
    private Instant endDate;
    private List<Integer> productIds;
    private List<Integer> customerIds;

    public CouponCreateDto(String code, BigDecimal discountValue, String discountType, int maxUsage, BigDecimal minOrderAmountLimit, Instant startDate, Instant endDate, List<Integer> productIds, List<Integer> customerIds) {
        this.code = code;
        this.discountValue = discountValue;
        this.discountType = discountType;
        this.maxUsage = maxUsage;
        this.minOrderAmountLimit = minOrderAmountLimit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productIds = productIds;
        this.customerIds = customerIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }

    public BigDecimal getMinOrderAmountLimit() {
        return minOrderAmountLimit;
    }

    public void setMinOrderAmountLimit(BigDecimal minOrderAmountLimit) {
        this.minOrderAmountLimit = minOrderAmountLimit;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }
}
