package com.example.ecommercebackend.controller.product.category;

import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryCreateDto), HttpStatus.CREATED);
    }
}
