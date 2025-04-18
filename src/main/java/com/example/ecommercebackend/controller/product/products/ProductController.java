package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.file.productimage.ProductImageUpdateDto;
import com.example.ecommercebackend.dto.product.products.*;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.service.product.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/simple")
    public ResponseEntity<Product> createSimpleProduct(@ModelAttribute ProductCreateDto productCreateDto) {
        return new ResponseEntity<>(productService.createSimpleProduct(productCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Product> deleteProduct(@RequestParam Integer id){
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> updateSimpleProduct(@RequestParam Integer id, @RequestBody ProductUpdateDto productUpdateDto) {
        return new ResponseEntity<>(productService.updateSimpleProduct(id, productUpdateDto), HttpStatus.OK);
    }

    @PutMapping("/product-image")
    public ResponseEntity<String> updateProductImage(@RequestParam Integer id, @ModelAttribute ProductImageUpdateDto productImageUpdateDto) {
        return new ResponseEntity<>(productService.updateProductImage(id, productImageUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/product-image")
    public ResponseEntity<String> deleteProductImage(@RequestParam Integer id,@RequestParam Integer productImageId) {
        return new ResponseEntity<>(productService.deleteProductImage(id,productImageId),HttpStatus.OK);
    }

    @PutMapping("/cover-image")
    public ResponseEntity<CoverImage> updateCoverImage(@RequestParam Integer id, @RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(productService.updateCoverImage(id, file), HttpStatus.OK);
    }

    @DeleteMapping("/cover-image")
    public ResponseEntity<String> deleteCoverImage(@RequestParam Integer id) {
        return new ResponseEntity<>(productService.deleteCoverImage(id),HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Product>> filter(@Valid @RequestBody ProductFilterRequest filterRequest,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(productService.filterProductsByCategory(filterRequest,page,size),HttpStatus.OK);
    }

    @PostMapping("/filter/small")
    public ResponseEntity<Set<ProductSmallDto>> filterSmall(@RequestBody ProductFilterRequest filterRequest,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        return new ResponseEntity<>(productService.filterProductsByCategorySmall(filterRequest,page,size),HttpStatus.OK);
    }

    @GetMapping("/name/{linkName}")
    public ResponseEntity<ProductDetailDto> findProductDetail(@PathVariable String linkName) {
        return new ResponseEntity<>(productService.findProductDetail(linkName),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
}
