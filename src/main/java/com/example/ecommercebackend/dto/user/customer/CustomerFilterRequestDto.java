package com.example.ecommercebackend.dto.user.customer;

import java.time.Instant;

public class CustomerFilterRequestDto {
    private String sortBy; // "createdAt", olacak
    private String sortDirection; // "desc"
    private Boolean isEnable;
    private Instant startDate;
    private Instant endDate;

    public CustomerFilterRequestDto(String sortBy, String sortDirection, Boolean isEnable, Instant startDate, Instant endDate) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.isEnable = isEnable;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
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
