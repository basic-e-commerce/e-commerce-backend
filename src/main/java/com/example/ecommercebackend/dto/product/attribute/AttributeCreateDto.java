package com.example.ecommercebackend.dto.product.attribute;

public class AttributeCreateDto {
    private String name;

    public AttributeCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
