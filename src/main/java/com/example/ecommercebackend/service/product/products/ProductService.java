package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.dto.file.ImageRequestDto;
import com.example.ecommercebackend.dto.file.ProductImageRequestDto;
import com.example.ecommercebackend.dto.product.products.ProductBuilder;
import com.example.ecommercebackend.dto.product.products.ProductCreateDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.file.ProductImage;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.ProductType;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import com.example.ecommercebackend.service.file.CoverImageService;
import com.example.ecommercebackend.service.file.ProductImageService;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CoverImageService coverImageService;
    private final ProductImageService productImageService;
    private final ProductBuilder productBuilder;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CoverImageService coverImageService, ProductImageService productImageService, ProductBuilder productBuilder, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.coverImageService = coverImageService;
        this.productImageService = productImageService;
        this.productBuilder = productBuilder;
        this.categoryService = categoryService;
    }

    public Product createProduct(ProductCreateDto productCreateDto) {

        if (productCreateDto.getName() == null || productCreateDto.getName().isEmpty())
            throw new BadRequestException("Product name cannot be empty");

        if (productCreateDto.getShortDescription() == null || productCreateDto.getShortDescription().length() > 165)
            throw new BadRequestException("ShortDescription cannot be longer than 165 characters");

        if (productCreateDto.getProductDescription() == null || productCreateDto.getProductDescription().isEmpty())
            throw new BadRequestException("Product description cannot be empty");

        if ((productCreateDto.getComparePrice() == null)
                || (productCreateDto.getComparePrice().doubleValue() < 0)
                || productCreateDto.getComparePrice().compareTo(productCreateDto.getSalePrice()) > 0){
            throw new BadRequestException("ComparePrice cannot be greater than SalePrice");
        }

        if (productCreateDto.getQuantity() < 0)
            throw new BadRequestException("Quantity cannot be less than 0");

        ProductType productType = ProductType.valueOf(productCreateDto.getProductType());


        Set<Category> categories = new HashSet<>();

        for (Integer categoryId : productCreateDto.getCategoryIds()) {
            Category category = categoryService.findCategoryById(categoryId);
            if (category.isSubCategory()){
                categories.add(category);
            }else
                throw new BadRequestException("Category is not a sub category");
        }

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Product product = productBuilder.productCreateDtoToProduct(productCreateDto,categories,productType,admin,admin);
            Product saveProduct = productRepository.save(product);

            if (productCreateDto.getCoverImage() != null){
                ImageRequestDto imageRequestDto = new ImageRequestDto(productCreateDto.getCoverImage());
                CoverImage coverImage = coverImageService.save(imageRequestDto,saveProduct.getId());
                saveProduct.setCoverImage(coverImage);
            }

            if (productCreateDto.getImages() != null){
                List<ProductImage> productImages = new ArrayList<>();
                for (int i = 0;i<productCreateDto.getImages().length;i++){
                    ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(productCreateDto.getImages()[i],i);
                    ProductImage productImage = productImageService.save(productImageRequestDto, saveProduct.getId());
                    productImages.add(productImage);
                }
                saveProduct.setProductImages(productImages);
            }
            return productRepository.save(saveProduct);
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }
}
