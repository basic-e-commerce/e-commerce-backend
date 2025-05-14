package com.example.ecommercebackend.dto.product.category;

import com.example.ecommercebackend.anotation.NotNullField;

public class CategoryUpdateDto {
    @NotNullField
    private int id;
    @NotNullField
    private final String name;
    @NotNullField
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
