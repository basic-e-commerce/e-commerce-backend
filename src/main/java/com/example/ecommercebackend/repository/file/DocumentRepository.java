package com.example.ecommercebackend.repository.file;

import com.example.ecommercebackend.entity.file.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
