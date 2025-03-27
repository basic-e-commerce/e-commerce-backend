package com.example.ecommercebackend.dto.product.category;

public class CategoryCreateDto {
    private String name;
    private String description;
    private int parentCategoryId;

    public CategoryCreateDto(String name, String description, int parentCategoryId) {
        this.name = name;
        this.description = description;
        this.parentCategoryId = parentCategoryId;
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
}
