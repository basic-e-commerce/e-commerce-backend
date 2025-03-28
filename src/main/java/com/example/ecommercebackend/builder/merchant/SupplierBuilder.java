package com.example.ecommercebackend.builder.merchant;

import com.example.ecommercebackend.dto.merchant.supplier.SupplierCreateDto;
import com.example.ecommercebackend.entity.merchant.Supplier;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Admin;
import org.springframework.stereotype.Component;

@Component
public class SupplierBuilder {

    public Supplier supplierCreateDtoToSupplier(SupplierCreateDto supplierCreateDto, Country country, Admin createAdmin,Admin updateAdmin) {
        return new Supplier(
                supplierCreateDto.getSupplierName(),
                supplierCreateDto.getCompanyName(),
                supplierCreateDto.getPhoneNumber(),
                supplierCreateDto.getAddressLine1(),
                supplierCreateDto.getAddressLine2(),
                country,
                supplierCreateDto.getCity(),
                supplierCreateDto.getNote(),
                createAdmin,
                updateAdmin
        );
    }
}
