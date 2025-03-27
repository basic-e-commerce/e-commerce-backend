package com.example.ecommercebackend.repository.product.attribute;

import com.example.ecommercebackend.entity.product.attribute.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Integer> {
}
