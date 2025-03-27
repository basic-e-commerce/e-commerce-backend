package com.example.ecommercebackend.builder.product.category;

import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.user.Admin;
import org.springframework.stereotype.Component;

@Component
public class CategoryBuilder {

    public Category CategoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto, Admin create,Admin update) {
        return new Category(categoryCreateDto.getName(), categoryCreateDto.getDescription(),create, update);
    }

}
