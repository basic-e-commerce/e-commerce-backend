package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SellRepository extends JpaRepository<Sell, Integer>, JpaSpecificationExecutor<Sell> {
}
