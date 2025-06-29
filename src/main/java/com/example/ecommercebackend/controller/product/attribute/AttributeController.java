package com.example.ecommercebackend.controller.product.attribute;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.attribute.AttributeCreateDto;
import com.example.ecommercebackend.entity.product.attribute.Attribute;
import com.example.ecommercebackend.service.product.attribute.AttributeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/attribute")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Attribute> createAttribute(@RequestBody AttributeCreateDto attributeCreateDto) {
        return new ResponseEntity<>(attributeService.createAttribute(attributeCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/id")
    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Attribute> findAttributeById(@RequestParam Integer id) {
        return new ResponseEntity<>(attributeService.findAttributeById(id), HttpStatus.OK);
    }

    @GetMapping
    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<Attribute>> findAllAttributes() {
        return new ResponseEntity<>(attributeService.findAll(),HttpStatus.OK);
    }

}
