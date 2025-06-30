package com.example.ecommercebackend.dto.product.invoice;

import com.example.ecommercebackend.anotation.NotNullField;
import com.example.ecommercebackend.anotation.NotNullParam;

public record CorporateInvoiceCreateDto(@NotNullField String companyName,
                                        @NotNullField String taxNumber,
                                        @NotNullField String taxOffice) {
}
