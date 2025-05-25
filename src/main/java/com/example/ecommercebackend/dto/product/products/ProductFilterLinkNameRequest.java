package com.example.ecommercebackend.dto.product.products;


import java.math.BigDecimal;

public class ProductFilterLinkNameRequest {
    private String linkName;
    private BigDecimal minPrice= BigDecimal.ZERO;
    private BigDecimal maxPrice = new BigDecimal("10000000");
    private String sortBy="productName"; // "price", "name" vb.
    private String sortDirection = "desc"; // "asc" veya "desc"


    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
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


    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
