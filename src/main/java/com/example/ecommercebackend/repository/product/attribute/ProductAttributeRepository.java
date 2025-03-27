package com.example.ecommercebackend.repository.product.attribute;

import com.example.ecommercebackend.entity.product.attribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
}
