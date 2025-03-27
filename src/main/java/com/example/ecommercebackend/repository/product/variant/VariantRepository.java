package com.example.ecommercebackend.repository.product.variant;

import com.example.ecommercebackend.entity.product.variant.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, Integer> {
}
