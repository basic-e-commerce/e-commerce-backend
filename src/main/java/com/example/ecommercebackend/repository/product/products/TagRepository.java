package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByTagNameEqualsIgnoreCase(String tagName);
}
