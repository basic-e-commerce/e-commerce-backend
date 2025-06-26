package com.example.ecommercebackend.dto.product.sell;

import java.time.Instant;

public class ProductSellDayFilterRequestDto {
    private Instant startDate;
    private Instant endDate;
    private Integer productId;
    private String periodType; // DAY, WEEK, MONTH, YEAR

    public ProductSellDayFilterRequestDto(Instant startDate, Instant endDate, Integer productId, String periodType) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
        this.periodType = periodType;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
