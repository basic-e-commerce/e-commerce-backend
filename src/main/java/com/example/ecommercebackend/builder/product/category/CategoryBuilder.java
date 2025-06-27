package com.example.ecommercebackend.builder.product.category;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.product.category.CategoryCreateDto;
import com.example.ecommercebackend.dto.product.category.CategoryDetailDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.user.Admin;
import org.springframework.stereotype.Component;

@Component
public class CategoryBuilder {

    public Category CategoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto) {
        return new Category(categoryCreateDto.getName(), categoryCreateDto.getDescription());
    }

    public CategoryDetailDto categoryToCategoryDetailDto(Category category) {
        ImageDetailDto imageDetailDto = null;
        if (category.getCoverImage() != null) {
            imageDetailDto=new ImageDetailDto(
                    category.getCoverImage().getId(),
                    category.getCoverImage().getName(),
                    category.getCoverImage().getResolution(),
                    category.getCoverImage().getName(),
                    category.getCoverImage().getUrl(),
                    0);
        }
        return new CategoryDetailDto(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryLinkName(),
                category.getCategoryDescription(),
                imageDetailDto,
                category.isSubCategory()
        );
    }

}
