package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.dto.file.ImageRequestDto;
import com.example.ecommercebackend.dto.file.ProductImageRequestDto;
import com.example.ecommercebackend.builder.product.products.ProductBuilder;
import com.example.ecommercebackend.dto.file.productimage.ProductImageUpdateDto;
import com.example.ecommercebackend.dto.product.products.ProductCreateDto;
import com.example.ecommercebackend.dto.product.products.ProductUpdateDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.file.ProductImage;
import com.example.ecommercebackend.entity.product.attribute.Attribute;
import com.example.ecommercebackend.entity.product.attribute.ProductAttribute;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.ProductType;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.attribute.ProductAttributeRepository;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import com.example.ecommercebackend.service.file.CoverImageService;
import com.example.ecommercebackend.service.file.ProductImageService;
import com.example.ecommercebackend.service.product.attribute.AttributeService;
import com.example.ecommercebackend.service.product.category.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CoverImageService coverImageService;
    private final ProductImageService productImageService;
    private final ProductBuilder productBuilder;
    private final CategoryService categoryService;
    private final ProductAttributeRepository productAttributeRepository;
    private final AttributeService attributeService;

    public ProductService(ProductRepository productRepository, CoverImageService coverImageService, ProductImageService productImageService, ProductBuilder productBuilder, CategoryService categoryService, ProductAttributeRepository productAttributeRepository, AttributeService attributeService) {
        this.productRepository = productRepository;
        this.coverImageService = coverImageService;
        this.productImageService = productImageService;
        this.productBuilder = productBuilder;
        this.categoryService = categoryService;
        this.productAttributeRepository = productAttributeRepository;
        this.attributeService = attributeService;
    }

    public Product createSimpleProduct(ProductCreateDto productCreateDto) {

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

    public Product updateSimpleProduct(int productId, ProductUpdateDto productUpdateDto){

        if (productUpdateDto.getName() == null || productUpdateDto.getName().isEmpty())
            throw new BadRequestException("Product name cannot be empty");

        if (productUpdateDto.getShortDescription() == null || productUpdateDto.getShortDescription().length() > 165)
            throw new BadRequestException("ShortDescription cannot be longer than 165 characters");

        if (productUpdateDto.getProductDescription() == null || productUpdateDto.getProductDescription().isEmpty())
            throw new BadRequestException("Product description cannot be empty");

        if ((productUpdateDto.getComparePrice() == null)
                || (productUpdateDto.getComparePrice().doubleValue() < 0)
                || productUpdateDto.getComparePrice().compareTo(productUpdateDto.getSalePrice()) > 0){
            throw new BadRequestException("ComparePrice cannot be greater than SalePrice");
        }

        if (productUpdateDto.getQuantity() < 0)
            throw new BadRequestException("Quantity cannot be less than 0");

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Product product = findProductById(productId);

            // Mevcut kategorileri al
            Set<Category> existingCategories = new HashSet<>(product.getCategories());

            // Yeni gelen kategori ID'lerini al
            Set<Integer> newCategoryIds = new HashSet<>(productUpdateDto.getCategoryIds());

            // Yeni eklenmesi gereken kategorileri bul
            Set<Category> categoriesToAdd = new HashSet<>();
            for (Integer categoryId : newCategoryIds) {
                if (existingCategories.stream().noneMatch(c -> c.getId() == categoryId)) {
                    categoriesToAdd.add(categoryService.findCategoryById(categoryId)); // Veritabanından bul
                }
            }

            // Çıkarılması gereken kategorileri bul
            Set<Category> categoriesToRemove = existingCategories.stream()
                    .filter(c -> !newCategoryIds.contains(c.getId()))
                    .collect(Collectors.toSet());

            // Kategorileri güncelle
            product.getCategories().removeAll(categoriesToRemove);
            product.getCategories().addAll(categoriesToAdd);

            if (productUpdateDto.getName() != null && !productUpdateDto.getName().equals(product.getProductName())) {
                product.setProductName(productUpdateDto.getName());
            }

            if (productUpdateDto.getSalePrice() != null && !productUpdateDto.getSalePrice().equals(product.getSalePrice())) {
                product.setSalePrice(productUpdateDto.getSalePrice());
            }

            if (productUpdateDto.getComparePrice() != null && !productUpdateDto.getComparePrice().equals(product.getComparePrice())) {
                product.setComparePrice(productUpdateDto.getComparePrice());
            }

            if (productUpdateDto.getBuyingPrice() != null && !productUpdateDto.getBuyingPrice().equals(product.getBuyingPrice())) {
                product.setBuyingPrice(productUpdateDto.getBuyingPrice());
            }

            if (productUpdateDto.getQuantity() != null && !productUpdateDto.getQuantity().equals(product.getQuantity())) {
                product.setQuantity(productUpdateDto.getQuantity());
            }

            if (productUpdateDto.getShortDescription() != null && !productUpdateDto.getShortDescription().equals(product.getShortDescription())) {
                product.setShortDescription(productUpdateDto.getShortDescription());
            }

            if (productUpdateDto.getProductDescription() != null && !productUpdateDto.getProductDescription().equals(product.getProductDescription())) {
                product.setProductDescription(productUpdateDto.getProductDescription());
            }


            if (productUpdateDto.getProductType() != null && !productUpdateDto.getProductType().equals(product.getProductType())) {
                ProductType productType = ProductType.valueOf(productUpdateDto.getProductType());
                product.setProductType(productType);
            }

            if (productUpdateDto.getPublished() != null && !productUpdateDto.getPublished().equals(product.getPublished())) {
                product.setPublished(productUpdateDto.getPublished());
            }

            if (productUpdateDto.getDisableOutOfStock() != null && !productUpdateDto.getDisableOutOfStock().equals(product.getDisableOutOfStock())) {
                product.setDisableOutOfStock(productUpdateDto.getDisableOutOfStock());
            }

            product.setUpdatedBy(admin);
            return productRepository.save(product);

        }else
            throw new BadRequestException("Authenticated user is not an Admin.");

    }

    public String updateProductImage(int productId, ProductImageUpdateDto productImageUpdateDto) {
        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Product product = findProductById(productId);

            for (Integer deleteImage : productImageUpdateDto.getDeleteImages()) {
                deleteProductImage(productId, deleteImage);
            }

            Set<ProductImage> newProductImages = new HashSet<>();
            for (MultipartFile newImage : productImageUpdateDto.getNewImages()) {
                ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(newImage,0);
                ProductImage save = productImageService.save(productImageRequestDto, productId);
                newProductImages.add(save);
            }

            product.getProductImages().addAll(newProductImages);
            product.setUpdatedBy(admin);
            productRepository.save(product);
            return "updated ProductImage";
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }

    public String deleteProductImage(int productId, int productImageId) {
        Product product = findProductById(productId);

        List<ProductImage> productImages = product.getProductImages();
        for (ProductImage productImage : productImages) {
            if (productImage.getId() == productImageId) {
                product.getProductImages().remove(productImage);
                productImageService.delete(productImage.getId());
            }
        }
        productRepository.save(product);
        return "deleted ProductImage";
    }

    public String deleteCoverImage(int productId) {
        Product product = findProductById(productId);
        if (product.getCoverImage() != null) {
            coverImageService.delete(product.getCoverImage().getId());
            product.setCoverImage(null);
            productRepository.save(product);
            return "deleted CoverImage";
        }else
            throw new NotFoundException("Cover image" + ExceptionMessage.NOT_FOUND.getMessage());
    }

    public CoverImage updateCoverImage(int productId, MultipartFile coverImage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Admin admin){
            Product product = findProductById(productId);
            if (product.getCoverImage() != null) {
                coverImageService.delete(product.getCoverImage().getId());
                product.setCoverImage(null);
                productRepository.save(product);
            }

            ImageRequestDto imageRequestDto = new ImageRequestDto(coverImage);
            CoverImage newCoverImage = coverImageService.save(imageRequestDto,product.getId());
            product.setCoverImage(newCoverImage);
            product.setUpdatedBy(admin);
            productRepository.save(product);
            return newCoverImage;
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }


    public Product deleteProduct(int productId) {
        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){

            Product product = findProductById(productId);
            product.setDeleted(true);
            product.setUpdatedBy(admin);
            return productRepository.save(product);
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }

    public ProductAttribute createProductAttribute(int productId,int attributeId) {
        Attribute attribute = attributeService.findAttributeById(attributeId);
        Product product = findProductById(productId);

        ProductAttribute productAttribute = new ProductAttribute(product,attribute);
        return productAttributeRepository.save(productAttribute);
    }

    // ------------- list product ------------------

    public Product findProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public boolean isExistProductById(int productId) {
        return productRepository.existsById(productId);
    }
}
