package com.example.ecommercebackend.dto.user;

import com.example.ecommercebackend.anotation.NotNullField;

import java.time.Instant;

public class TimeDto {
    @NotNullField
    private Instant startDate;
    @NotNullField
    private Instant endDate;

    public TimeDto(Instant startDate, Instant endDate) {
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
