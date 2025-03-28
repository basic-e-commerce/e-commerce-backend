package com.example.ecommercebackend.repository.product.attribute;

import com.example.ecommercebackend.entity.product.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    boolean existsByAttributeNameEqualsIgnoreCase(String attributeName);
}
