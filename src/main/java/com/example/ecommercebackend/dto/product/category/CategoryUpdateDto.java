package com.example.ecommercebackend.dto.product.category;

import org.springframework.web.multipart.MultipartFile;

public class CategoryUpdateDto {
    private int id;
    private final String name;
    private final String description;


    public CategoryUpdateDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
