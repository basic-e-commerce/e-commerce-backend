package com.example.ecommercebackend.dto.product.sell;

import java.time.Instant;

public class ProductSellDayFilterRequestDto {
    private Instant startDate;
    private Instant endDate;

    public ProductSellDayFilterRequestDto(Instant startDate, Instant endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
