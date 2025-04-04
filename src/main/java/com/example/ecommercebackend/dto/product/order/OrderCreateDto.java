package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.dto.product.invoice.CorporateInvoiceCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressOrderCreateDto;

import java.util.List;

public record OrderCreateDto(AddressOrderCreateDto address,
                             String invoiceType,
                             CorporateInvoiceCreateDto corporateInvoice,
                             List<OrderItemCreateDto> orderItemCreateDtos) {
}
