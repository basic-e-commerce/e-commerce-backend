package com.example.ecommercebackend.repository.product.variant;

import com.example.ecommercebackend.entity.product.variant.VariantOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantOptionRepository extends JpaRepository<VariantOption, Integer> {
}
