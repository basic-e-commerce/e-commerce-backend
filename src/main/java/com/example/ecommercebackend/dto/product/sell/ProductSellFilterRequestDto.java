package com.example.ecommercebackend.dto.product.sell;

import java.time.Instant;

public class ProductSellFilterRequestDto {
    private Integer productId;
    private String sortBy;          // "price", "name" vb.
    private String sortDirection;   // "asc" veya "desc"
    private Instant startDate;
    private Instant endDate;

    public ProductSellFilterRequestDto(Integer productId, String sortBy, String sortDirection, Instant startDate, Instant endDate) {
        this.productId = productId;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
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
}
