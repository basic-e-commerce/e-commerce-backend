package com.example.ecommercebackend.service.storagestrategy;


import com.example.ecommercebackend.dto.file.FilePropertiesDto;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageStrategy {
    FilePropertiesDto saveFile(MultipartFile file, String fileName); // Dosyayı kaydet
    void deleteFile(String filePath); // Dosyayı sil
    boolean exists(String filePath);

}
