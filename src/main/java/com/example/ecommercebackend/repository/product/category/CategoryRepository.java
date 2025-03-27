package com.example.ecommercebackend.repository.product.category;

import com.example.ecommercebackend.entity.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameEqualsIgnoreCase(String name);
}
