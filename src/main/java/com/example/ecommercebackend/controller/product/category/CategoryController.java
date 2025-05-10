package com.example.ecommercebackend.controller.product.category;

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

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDetailDto> createCategory(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryCreateDto), HttpStatus.CREATED);
    }


    @GetMapping("/parent")
    public ResponseEntity<List<Category>> getCategoryParent() {
        return new ResponseEntity<>(categoryService.findParentCategories(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CategoryDetailDto> deleteCategory(@RequestParam Integer id) {
        return new ResponseEntity<>(categoryService.deleteCategory(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoryDetailDto> updateCategory(@RequestBody CategoryUpdateDto categoryUpdateDto){
        return new ResponseEntity<>(categoryService.updateCategory(categoryUpdateDto),HttpStatus.OK);
    }

    @PutMapping("/image")
    public ResponseEntity<CategoryDetailDto> updateCategoryImage(@RequestParam Integer id, @RequestParam MultipartFile image) {
        return new ResponseEntity<>(categoryService.updateCategoryImage(id,image),HttpStatus.OK);
    }

    @DeleteMapping("/image")
    public ResponseEntity<String> deleteCategoryImage(@RequestParam Integer id) {
        return new ResponseEntity<>(categoryService.deleteCategoryImage(id),HttpStatus.OK);
    }


}
