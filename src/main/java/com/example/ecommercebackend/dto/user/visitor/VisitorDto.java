package com.example.ecommercebackend.dto.user.visitor;

import java.time.LocalDate;
import java.util.Map;

public class VisitorDto {
    Map<LocalDate,Long> visitors;
    private Long quantity;

    public VisitorDto(Map<LocalDate, Long> visitors, Long quantity) {
        this.visitors = visitors;
        this.quantity = quantity;
    }

    public Map<LocalDate, Long> getVisitors() {
        return visitors;
    }

    public void setVisitors(Map<LocalDate, Long> visitors) {
        this.visitors = visitors;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
