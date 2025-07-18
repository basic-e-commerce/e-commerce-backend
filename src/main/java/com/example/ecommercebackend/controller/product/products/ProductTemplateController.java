package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.product.products.productTemplate.ProductTemplateDto;
import com.example.ecommercebackend.dto.product.products.productTemplate.ProductTemplateResponseDto;
import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import com.example.ecommercebackend.service.product.products.ProductTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-template")
public class ProductTemplateController {
    private final ProductTemplateService productTemplateService;

    public ProductTemplateController(ProductTemplateService productTemplateService) {
        this.productTemplateService = productTemplateService;
    }

    @PostMapping
    public ResponseEntity<ProductTemplateResponseDto> create(@RequestBody ProductTemplateDto productTemplate) {
        return new ResponseEntity<>(productTemplateService.createProductTemplate(productTemplate), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductTemplateResponseDto>> getAll() {
        return new ResponseEntity<>(productTemplateService.listProductTemplates(), HttpStatus.OK);
    }
}
