package com.example.ecommercebackend.repository.merchant;

import com.example.ecommercebackend.entity.merchant.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    boolean existsBySupplierNameEqualsIgnoreCase(String supplierName);

}
