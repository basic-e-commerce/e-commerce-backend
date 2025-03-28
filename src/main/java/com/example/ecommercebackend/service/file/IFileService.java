package com.example.ecommercebackend.service.file;

import com.example.ecommercebackend.dto.file.FileRequestDto;
import com.example.ecommercebackend.entity.file.File;
import com.example.ecommercebackend.service.storagestrategy.IStorageStrategy;

import java.util.List;

public interface IFileService<T extends File,R extends FileRequestDto> {
    T save(R file,Long id);  // Dosya kaydetme
    String delete(Long id);  // Dosya silme
    T getById(Long id);  // Belirli ID'ye sahip dosyayı getir
    List<T> getAll();  // Tüm dosyaları getir
    void setStorageStrategy(IStorageStrategy strategy); // Depolama türünü belirle
    String getFileExtension(String fileName);
}
