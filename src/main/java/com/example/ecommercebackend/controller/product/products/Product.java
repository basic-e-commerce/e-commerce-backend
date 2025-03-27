package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.service.product.products.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class Product {
    private final ProductService productService;

    public Product(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String sayHello() {
        return "Manziryama aşığım";
    }
}
