package com.example.ecommercebackend.dto.product.category;

import com.example.ecommercebackend.anotation.NotNullField;
import org.springframework.web.multipart.MultipartFile;

public class CategoryCreateDto {
    @NotNullField
    private final String name;
    @NotNullField
    private final String description;
    @NotNullField
    private final Integer parentCategoryId;

    private MultipartFile image;

    public CategoryCreateDto(String name, String description, Integer parentCategoryId, MultipartFile image) {
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

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
