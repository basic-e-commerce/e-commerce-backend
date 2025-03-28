package com.example.ecommercebackend.controller.merchant;

import com.example.ecommercebackend.dto.merchant.supplier.SupplierCreateDto;
import com.example.ecommercebackend.entity.merchant.Supplier;
import com.example.ecommercebackend.service.merchant.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierCreateDto supplierCreateDto) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSupplier(@RequestParam Integer id){
        return new ResponseEntity<>(supplierService.deleteSupplier(id),HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Supplier> findSupplierById(@RequestParam Integer id){
        return new ResponseEntity<>(supplierService.findSupplierById(id),HttpStatus.OK);
    }

}
