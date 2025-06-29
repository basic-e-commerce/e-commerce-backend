package com.example.ecommercebackend.controller.merchant;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.merchant.supplier.SupplierCreateDto;
import com.example.ecommercebackend.dto.product.supplier.SupplierDetailDto;
import com.example.ecommercebackend.entity.merchant.Supplier;
import com.example.ecommercebackend.service.merchant.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<SupplierDetailDto> createSupplier(@RequestBody SupplierCreateDto supplierCreateDto) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> deleteSupplier(@RequestParam Integer id){
        return new ResponseEntity<>(supplierService.deleteSupplier(id),HttpStatus.OK);
    }


    @GetMapping("/id")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Supplier> findSupplierById(@RequestParam Integer id){
        return new ResponseEntity<>(supplierService.findSupplierById(id),HttpStatus.OK);
    }

}
