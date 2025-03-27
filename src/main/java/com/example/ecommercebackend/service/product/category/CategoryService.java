package com.example.ecommercebackend.service.product.category;

import com.example.ecommercebackend.builder.product.category.CategoryBuilder;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
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

        if (categoryRepository.existsByCategoryNameEqualsIgnoreCase(categoryCreateDto.getName())){
            throw new IllegalArgumentException("Category name already exists.");
        }

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin) {
            Category parentCategory = null;
            if (categoryCreateDto.getParentCategoryId() != 0){
                parentCategory = findCategoryById(categoryCreateDto.getParentCategoryId());
            }
            Category category = categoryBuilder.CategoryCreateDtoToCategory(categoryCreateDto,admin,admin);
            category.setParentCategory(parentCategory);
            Category saveCategory = categoryRepository.save(category);
            if (parentCategory != null){
                parentCategory.setSubCategory(false);
                categoryRepository.save(parentCategory);
            }
            return saveCategory;
        } else {
            throw new IllegalArgumentException("Authenticated user is not an Admin.");
        }

    }

    public Category findCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException(ExceptionMessage.CATEGORY_NOT_FOUND.getMessage()));
    }
}
