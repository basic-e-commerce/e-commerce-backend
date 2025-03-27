package com.example.ecommercebackend.repository.product.attribute;

import com.example.ecommercebackend.entity.product.attribute.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
}
