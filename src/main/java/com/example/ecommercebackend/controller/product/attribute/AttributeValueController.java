package com.example.ecommercebackend.controller.product.attribute;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.attribute.attributevalue.AttributeValueCreateDto;
import com.example.ecommercebackend.entity.product.attribute.AttributeValue;
import com.example.ecommercebackend.repository.product.attribute.AttributeValueRepository;
import com.example.ecommercebackend.service.product.attribute.AttributeValueService;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/attribute-value")
public class AttributeValueController {
    private final AttributeValueService attributeValueService;

    public AttributeValueController(AttributeValueService attributeValueService) {
        this.attributeValueService = attributeValueService;
    }

    @PostMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<AttributeValue> createAttributeValue(@RequestBody AttributeValueCreateDto attributeValueCreateDto) {
        return new ResponseEntity<>(attributeValueService.createAttributeValue(attributeValueCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/id")
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<AttributeValue> getAttributeValueById(@RequestParam("id") int id) {
        return new ResponseEntity<>(attributeValueService.findAttributeValueById(id), HttpStatus.OK);
    }

    @GetMapping("/attribute")
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<AttributeValue>> getAllAttributeValue(@RequestParam int id) {
        return new ResponseEntity<>(attributeValueService.findAttributeValueByAttribute(id), HttpStatus.OK);
    }

    @GetMapping
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<AttributeValue>> getAllAttributeValues() {
        return new ResponseEntity<>(attributeValueService.findAll(),HttpStatus.OK);
    }


}
