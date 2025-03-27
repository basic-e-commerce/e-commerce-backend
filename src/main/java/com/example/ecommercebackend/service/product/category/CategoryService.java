package com.example.ecommercebackend.service.product.category;

import com.example.ecommercebackend.builder.product.category.CategoryBuilder;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.repository.product.category.CategoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryBuilder categoryBuilder;

    public CategoryService(CategoryRepository categoryRepository, CategoryBuilder categoryBuilder) {
        this.categoryRepository = categoryRepository;
        this.categoryBuilder = categoryBuilder;
    }

    public Category createCategory(CategoryCreateDto categoryCreateDto) {

        if (categoryCreateDto.getName() == null || categoryCreateDto.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        if (categoryRepository.existsByNameEqualsIgnoreCase(categoryCreateDto.getName())){
            throw new IllegalArgumentException("Category name already exists.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Category category = categoryBuilder.CategoryCreateDtoToCategory(categoryCreateDto,new Admin(),new Admin());
        if (categoryCreateDto.getParentCategoryId() == 0){
            
        }
        return null;
    }
}
