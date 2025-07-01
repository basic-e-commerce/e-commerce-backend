package com.example.ecommercebackend.dto.product.products.coupon;

import com.example.ecommercebackend.anotation.NotNullField;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CouponCreateDto {
    @NotNullField
    private String code;
    @NotNullField
    private String description;
    @NotNullField
    private BigDecimal discountValue;  // eğer yüzdelikse 100 den büyük olamaz. diğeriyse sayısal değer verilir.
    @NotNullField
    private String discountType;
    @NotNullField
    private Integer tatalUsageLimit;
    @NotNullField
    private BigDecimal minOrderAmountLimit;
    @NotNullField
    private Instant startDate;
    @NotNullField
    private Instant endDate;
    private List<Integer> productIds = new ArrayList<>();
    private List<Integer> customerIds = new ArrayList<>();
    @NotNullField
    private Boolean isPublic;
    @NotNullField
    private Boolean isActive;

    public CouponCreateDto(String code, String description, BigDecimal discountValue, String discountType, Integer tatalUsageLimit, BigDecimal minOrderAmountLimit, Instant startDate, Instant endDate, List<Integer> productIds, List<Integer> customerIds, Boolean isPublic, Boolean isActive) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.discountType = discountType;
        this.tatalUsageLimit = tatalUsageLimit;
        this.minOrderAmountLimit = minOrderAmountLimit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productIds = productIds;
        this.customerIds = customerIds;
        this.isPublic = isPublic;
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

    public Integer getTatalUsageLimit() {
        return tatalUsageLimit;
    }

    public void setTatalUsageLimit(Integer tatalUsageLimit) {
        this.tatalUsageLimit = tatalUsageLimit;
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
