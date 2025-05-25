package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;
import java.time.Instant;

public class SellDetailRequestDto {
    private Instant startDate;
    private Instant endDate;
    private String productLinkName;

    private String sortBy="productName"; // "price", "name" vb.
    private String sortDirection = "desc"; // "asc" veya "desc"

    public SellDetailRequestDto(Instant startDate, Instant endDate, String productLinkName, String sortBy, String sortDirection) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.productLinkName = productLinkName;
        this.sortBy = sortBy;
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

    public String getProductLinkName() {
        return productLinkName;
    }

    public void setProductLinkName(String productLinkName) {
        this.productLinkName = productLinkName;
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
}
