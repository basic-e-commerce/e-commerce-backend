package com.example.ecommercebackend.repository.file;

import com.example.ecommercebackend.entity.file.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
