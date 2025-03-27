package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
