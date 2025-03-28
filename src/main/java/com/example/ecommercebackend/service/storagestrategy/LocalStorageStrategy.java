package com.example.ecommercebackend.service.storagestrategy;

import com.example.ecommercebackend.dto.file.FilePropertiesDto;
import jakarta.transaction.Transactional;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

public class LocalStorageStrategy implements IStorageStrategy{
    @Transactional
    @Override
    public FilePropertiesDto saveFile(MultipartFile file, String directoryPath) {
        // directoryPath tam olarak konumu göstermelidir.
        // /var/www/upload/ecommerce/coverimage/{product_id}/ gibi

        Path path = Paths.get(directoryPath);

        try {
            // Klasör yoksa oluştur
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Directory created: " + directoryPath);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            // Extension'ı al
            String fileExtension = getFileExtension(originalFileName);

            // Orijinal isimden extension'ı ayır
            String originalName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));

            // Dosya ismini normalize et
            String safeName = normalizeFileName(originalName);

            // İlk dosya adı oluştur
            String finalFileName = safeName + "." + fileExtension;
            Path filePath = path.resolve(finalFileName);
            System.out.println("filePath-------------: " + filePath);

            // Aynı isimde varsa sonuna _1, _2 ekle
            int counter = 1;
            while (Files.exists(filePath)) {
                finalFileName = safeName + "_" + counter + "." + fileExtension;
                System.out.println("newFileName------------: " + finalFileName);
                filePath = path.resolve(finalFileName);
                counter++;
            }

            // Dosyayı kaydet
            file.transferTo(filePath.toFile());

            // Dosya bilgilerini al
            long fileSize = file.getSize();
            String format = detectFileType(file);
            String resolution = format.startsWith("image/") ? getImageResolution(filePath) : "0";

            return new FilePropertiesDto(finalFileName, fileSize, resolution, fileExtension);

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("File name is invalid");
        }
        if (!exists(filePath)) {
            throw new RuntimeException("File not found: " + filePath);
        }
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("File deleted: " + filePath);
        }else
            throw new RuntimeException("Unable to delete file: " + filePath);

    }

    @Override
    public boolean exists(String filePath) {
        File file = new File(filePath);
        if (file.exists())
            return true;
        else
            return false;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return ""; // Uzantı yoksa veya sadece nokta varsa boş string döndür
        }
        return fileName.substring(lastDotIndex + 1); // Noktasız döndür
    }

    // --- normalizeFileName ---
    private String normalizeFileName(String fileName) {
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD);
        // Türkçe ve özel karakterleri temizle
        return normalized.replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    // MIME türünü belirleme (Tika kullanarak daha güvenilir hale getirildi)
    private String detectFileType(MultipartFile file) {
        Tika tika = new Tika();
        try {
            return tika.detect(file.getInputStream());
        } catch (IOException e) {
            return "unknown";
        }
    }

    // Görsel çözünürlüğünü almak için bir metot ekle (sadece görseller için)
    private String getImageResolution(Path filePath) {
        try {
            BufferedImage image = ImageIO.read(filePath.toFile());
            if (image != null) {
                return image.getWidth() + "x" + image.getHeight();
            }
        } catch (IOException e) {
            System.out.println("Could not determine image resolution: " + e.getMessage());
        }
        return "Unknown";
    }
}
