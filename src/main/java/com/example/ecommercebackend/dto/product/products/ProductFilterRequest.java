package com.example.ecommercebackend.dto.product.products;

import java.math.BigDecimal;

public class ProductFilterRequest {
    private final Integer categoryId;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final String sortBy; // "price", "name" vb.
    private final String sortDirection; // "asc" veya "desc"

    public ProductFilterRequest(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sortBy, String sortDirection) {
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

}
