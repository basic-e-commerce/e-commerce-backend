package com.example.ecommercebackend.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EncryptedStringConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        return EncryptionUtils.encrypt(attribute);  // statik method
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return EncryptionUtils.decrypt(dbData);    // statik method
    }
}
