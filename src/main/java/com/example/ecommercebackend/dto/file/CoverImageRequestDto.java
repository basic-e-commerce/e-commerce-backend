package com.example.ecommercebackend.dto.file;

import org.springframework.web.multipart.MultipartFile;

public class CoverImageRequestDto extends ImageRequestDto {
    public CoverImageRequestDto(MultipartFile multipartFile) {
        super(multipartFile);
    }
}
