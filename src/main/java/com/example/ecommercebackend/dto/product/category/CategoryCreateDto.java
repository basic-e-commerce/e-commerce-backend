package com.example.ecommercebackend.dto.product.category;

import org.springframework.web.multipart.MultipartFile;

public class CategoryCreateDto {
    private String name;
    private String description;
    private int parentCategoryId;
    private MultipartFile image;

    public CategoryCreateDto(String name, String description, int parentCategoryId, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.parentCategoryId = parentCategoryId;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public MultipartFile getImage() {
        return image;
    }

}
