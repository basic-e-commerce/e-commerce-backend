package com.example.ecommercebackend.repository.product.attribute;

import com.example.ecommercebackend.entity.product.attribute.Attribute;
import com.example.ecommercebackend.entity.product.attribute.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
    List<AttributeValue> findByAttribute(Attribute attribute);

}
