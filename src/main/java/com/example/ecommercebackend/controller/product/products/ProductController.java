package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.product.products.ProductCreateDto;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.service.product.products.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductCreateDto productCreateDto) {
        return new ResponseEntity<>(productService.createProduct(productCreateDto), HttpStatus.CREATED);
    }
}
