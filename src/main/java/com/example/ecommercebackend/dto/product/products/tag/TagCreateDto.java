package com.example.ecommercebackend.dto.product.products.tag;

public class TagCreateDto {
    private String tagName;

    public TagCreateDto(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

}
