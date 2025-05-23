package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.file.ImageDetailDto;
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


    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/simple")
    public ResponseEntity<ProductAdminDetailDto> createSimpleProduct(@ModelAttribute ProductCreateDto productCreateDto) {
        return new ResponseEntity<>(productService.createSimpleProduct(productCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ProductAdminDetailDto> deleteProduct(@RequestParam(required = false) Integer id){
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductAdminDetailDto> updateSimpleProduct(@RequestParam(required = false) Integer id, @RequestBody(required = false) ProductUpdateDto productUpdateDto) {
        return new ResponseEntity<>(productService.updateSimpleProduct(id, productUpdateDto), HttpStatus.OK);
    }

    @PutMapping("/product-image")
    public ResponseEntity<String> updateProductImage(@RequestParam(required = false) Integer id, @ModelAttribute ProductImageUpdateDto productImageUpdateDto) {
        return new ResponseEntity<>(productService.updateProductImage(id, productImageUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/product-image")
    public ResponseEntity<String> deleteProductImage(@RequestParam(required = false) Integer id,@RequestParam Integer productImageId) {
        return new ResponseEntity<>(productService.deleteProductImage(id,productImageId),HttpStatus.OK);
    }

    @PutMapping("/cover-image")
    public ResponseEntity<ImageDetailDto> updateCoverImage(@RequestParam(required = false) Integer id, @RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(productService.updateCoverImage(id, file), HttpStatus.OK);
    }

    @DeleteMapping("/cover-image")
    public ResponseEntity<String> deleteCoverImage(@RequestParam(required = false) Integer id) {
        return new ResponseEntity<>(productService.deleteCoverImage(id),HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductAdminDetailDto>> filter(@RequestBody(required = false) ProductFilterRequest filterRequest,
                                                @RequestParam(defaultValue = "0",required = false) Integer page,
                                                @RequestParam(defaultValue = "10",required = false) Integer size) {
        return new ResponseEntity<>(productService.filterProductsByCategory(filterRequest,page,size),HttpStatus.OK);
    }


    @PostMapping("/filter/small")
    public ResponseEntity<Set<ProductSmallDto>> filterSmall(@RequestBody(required = false) ProductFilterRequest filterRequest,
                                                            @RequestParam(defaultValue = "0",required = false) Integer page,
                                                            @RequestParam(defaultValue = "10",required = false) Integer size){
        return new ResponseEntity<>(productService.filterProductsByCategorySmall(filterRequest,page,size),HttpStatus.OK);
    }

    @GetMapping("/name/{linkName}")
    public ResponseEntity<ProductDetailDto> findProductDetail(@PathVariable(required = false) String linkName) {
        return new ResponseEntity<>(productService.findProductDetail(linkName),HttpStatus.OK);
    }

    @GetMapping("/name/admin/{linkName}")
    public ResponseEntity<Product> findProductDetailAdmin(@PathVariable(required = false) String linkName) {
        return new ResponseEntity<>(productService.findProductDetailAdmin(linkName),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
}
