package com.example.ecommercebackend.dto.product.sell;

import java.math.BigDecimal;
import java.time.Instant;

public class SellDetailRequestDto {
    private Instant startDate;
    private Instant endDate;

    private BigDecimal minPrice= BigDecimal.ZERO;
    private BigDecimal maxPrice = new BigDecimal("10000000");
    private String sortBy="productName"; // "price", "name" vb.
    private String sortDirection = "desc"; // "asc" veya "desc"

}
