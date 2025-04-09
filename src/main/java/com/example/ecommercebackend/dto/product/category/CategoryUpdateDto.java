package com.example.ecommercebackend.dto.product.category;

import org.springframework.web.multipart.MultipartFile;

public class CategoryUpdateDto {
    private int id;
    private final String name;
    private final String description;
    private final boolean active;

    public CategoryUpdateDto(int id, String name, String description, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public int getId() {
        return id;
    }
}
