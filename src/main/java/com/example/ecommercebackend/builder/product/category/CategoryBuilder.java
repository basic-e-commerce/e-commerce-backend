package com.example.ecommercebackend.builder.product.category;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryDetailDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.user.Admin;
import org.springframework.stereotype.Component;

@Component
public class CategoryBuilder {

    public Category CategoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto, Admin create,Admin update) {
        return new Category(categoryCreateDto.getName(), categoryCreateDto.getDescription(),create, update);
    }

    public CategoryDetailDto categoryToCategoryDetailDto(Category category) {
        return new CategoryDetailDto(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryLinkName(),
                category.getCategoryDescription(),
                new ImageDetailDto(
                        category.getCoverImage().getId(),
                        category.getCoverImage().getName(),
                        category.getCoverImage().getResolution(),
                        category.getCoverImage().getName(),
                        category.getCoverImage().getUrl(),
                        0),
                category.isSubCategory()
        );
    }

}
