package com.example.ecommercebackend.dto.file.productimage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageUpdateDto {
    private MultipartFile[] newImages;
    private List<Integer> deleteImages;

    public ProductImageUpdateDto(MultipartFile[] newImages, List<Integer> deleteImages) {
        this.newImages = newImages;
        this.deleteImages = deleteImages;
    }

    public MultipartFile[] getNewImages() {
        return newImages;
    }

    public List<Integer> getDeleteImages() {
        return deleteImages;
    }
}
