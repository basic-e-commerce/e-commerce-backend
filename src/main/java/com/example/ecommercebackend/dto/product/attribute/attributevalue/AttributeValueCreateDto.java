package com.example.ecommercebackend.dto.product.attribute.attributevalue;

public class AttributeValueCreateDto {
    private String name;
    private int attributeId;

    public AttributeValueCreateDto(String name, int attributeId) {
        this.name = name;
        this.attributeId = attributeId;
    }

    public String getName() {
        return name;
    }

    public int getAttributeId() {
        return attributeId;
    }
}
