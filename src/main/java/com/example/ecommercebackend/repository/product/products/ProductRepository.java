package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    boolean existsByProductNameEqualsIgnoreCase(String productName);
    Optional<Product> findByProductLinkName(String productLinkName);
}
