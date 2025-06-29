package com.example.ecommercebackend.controller.product.category;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryDetailDto;
import com.example.ecommercebackend.dto.product.category.CategoryUpdateDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping
    public ResponseEntity<CategoryDetailDto> createCategory(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryCreateDto), HttpStatus.CREATED);
    }

    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/parent")
    public ResponseEntity<List<Category>> getCategoryParent() {
        return new ResponseEntity<>(categoryService.findParentCategories(), HttpStatus.OK);
    }
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/by-link-name")
    public ResponseEntity<CategoryDetailDto> getCategoryByLinkName(@RequestParam("linkName") String linkName) {
        return new ResponseEntity<>(categoryService.getCategoryByLinkName(linkName),HttpStatus.OK);
    }

    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @DeleteMapping
    public ResponseEntity<CategoryDetailDto> deleteCategory(@RequestParam(required = false) Integer id) {
        return new ResponseEntity<>(categoryService.deleteCategory(id),HttpStatus.OK);
    }

    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PutMapping
    public ResponseEntity<CategoryDetailDto> updateCategory(@RequestBody(required = false) CategoryUpdateDto categoryUpdateDto){
        return new ResponseEntity<>(categoryService.updateCategory(categoryUpdateDto),HttpStatus.OK);
    }

    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PutMapping("/image")
    public ResponseEntity<CategoryDetailDto> updateCategoryImage(@RequestParam(required = false) Integer id, @RequestParam(required = false) MultipartFile image) {
        return new ResponseEntity<>(categoryService.updateCategoryImage(id,image),HttpStatus.OK);
    }

    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @DeleteMapping("/image")
    public ResponseEntity<String> deleteCategoryImage(@RequestParam(required = false) Integer id) {
        return new ResponseEntity<>(categoryService.deleteCategoryImage(id),HttpStatus.OK);
    }


}
