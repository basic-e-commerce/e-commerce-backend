package com.example.ecommercebackend.service.merchant;

import com.example.ecommercebackend.builder.merchant.SupplierBuilder;
import com.example.ecommercebackend.dto.merchant.supplier.SupplierCreateDto;
import com.example.ecommercebackend.dto.product.supplier.SupplierDetailDto;
import com.example.ecommercebackend.entity.merchant.Supplier;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.merchant.SupplierRepository;
import com.example.ecommercebackend.service.product.shipping.CountryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    private final CountryService countryService;
    private final SupplierRepository supplierRepository;
    private final SupplierBuilder supplierBuilder;
    public SupplierService(CountryService countryService, SupplierRepository supplierRepository, SupplierBuilder supplierBuilder) {
        this.countryService = countryService;
        this.supplierRepository = supplierRepository;
        this.supplierBuilder = supplierBuilder;
    }

    public SupplierDetailDto createSupplier(SupplierCreateDto supplierCreateDto) {
        if (supplierRepository.existsBySupplierNameEqualsIgnoreCase(supplierCreateDto.getSupplierName()))
            throw new ResourceAlreadyExistException("Supplier "+ ExceptionMessage.ALREADY_EXISTS.getMessage());

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Supplier supplier = supplierBuilder.supplierCreateDtoToSupplier(supplierCreateDto,countryService.findCountryByIso3("TUR"),admin,admin);
            return supplierBuilder.supplierToSupplierDetailDto(supplierRepository.save(supplier));
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");

    }

    public String deleteSupplier(Integer id) {
        Supplier supplier = findSupplierById(id);
        if (!supplier.getProducts().isEmpty())
            throw new BadRequestException("Supplier already has products.");

        supplierRepository.delete(supplier);
        return supplier.getSupplierName() + " deleted";
    }

    public Supplier findSupplierById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(()-> new NotFoundException("Supplier "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }
}
