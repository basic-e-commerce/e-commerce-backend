package com.example.ecommercebackend.service.file;

import com.example.ecommercebackend.dto.file.FileRequestDto;
import com.example.ecommercebackend.entity.file.Image;

public interface ImageService<T extends Image,R extends FileRequestDto> extends IFileService<T,R> {
    boolean isValidExtension(String extension);
}
