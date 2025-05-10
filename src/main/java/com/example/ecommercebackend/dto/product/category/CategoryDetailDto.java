package com.example.ecommercebackend.dto.product.category;

import com.example.ecommercebackend.dto.file.ImageDetailDto;

public class CategoryDetailDto {
    private int id;
    private String name;
    private String linkName;
    private String description;
    private ImageDetailDto coverImage;
    private boolean isSubCategory;

    public CategoryDetailDto(int id, String name, String linkName, String description, ImageDetailDto coverImage, boolean isSubCategory) {
        this.id = id;
        this.name = name;
        this.linkName = linkName;
        this.description = description;
        this.coverImage = coverImage;
        this.isSubCategory = isSubCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageDetailDto getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageDetailDto coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isSubCategory() {
        return isSubCategory;
    }

    public void setSubCategory(boolean subCategory) {
        isSubCategory = subCategory;
    }
}
