package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductTemplateRepository extends JpaRepository<ProductTemplate,Integer>, JpaSpecificationExecutor<ProductTemplate> {
    Boolean existsByName(String name);
}
