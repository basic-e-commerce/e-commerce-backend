package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.file.ImageRequestDto;
import com.example.ecommercebackend.dto.file.ProductImageRequestDto;
import com.example.ecommercebackend.builder.product.products.ProductBuilder;
import com.example.ecommercebackend.dto.file.productimage.ProductImageUpdateDto;
import com.example.ecommercebackend.dto.product.products.*;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.file.ProductImage;
import com.example.ecommercebackend.entity.product.attribute.Attribute;
import com.example.ecommercebackend.entity.product.attribute.ProductAttribute;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.ProductType;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.attribute.ProductAttributeRepository;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import com.example.ecommercebackend.service.file.CoverImageService;
import com.example.ecommercebackend.service.file.ProductImageService;
import com.example.ecommercebackend.service.product.attribute.AttributeService;
import com.example.ecommercebackend.service.product.category.CategoryService;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
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

    public ProductAdminDetailDto createSimpleProduct(@NotNullParam ProductCreateDto productCreateDto) {

        if (productCreateDto.getName().isEmpty())
            throw new BadRequestException("Product name cannot be empty");

        if (productCreateDto.getShortDescription().length() > 165)
            throw new BadRequestException("ShortDescription cannot be longer than 165 characters");

        if (productCreateDto.getDescription().isEmpty())
            throw new BadRequestException("Product description cannot be empty");

        if ((productCreateDto.getComparePrice().doubleValue() < 0) || productCreateDto.getComparePrice().compareTo(productCreateDto.getSalePrice()) > 0){
            throw new BadRequestException("ComparePrice cannot be greater than SalePrice");
        }

        if (productCreateDto.getQuantity() < 0)
            throw new BadRequestException("Quantity cannot be less than 0");

        if (productCreateDto.getTaxRate().compareTo(BigDecimal.valueOf(100)) > 0 || productCreateDto.getTaxRate().compareTo(BigDecimal.ZERO) < 0)
            throw new BadRequestException("Tax rate must be between 0 and 100");

        if (productRepository.existsByProductNameEqualsIgnoreCase(productCreateDto.getName()))
            throw new ResourceAlreadyExistException("Product name already exists");

        ProductType productType = ProductType.valueOf(productCreateDto.getProductType());

        Set<Category> categories = new HashSet<>();

        for (Integer categoryId : productCreateDto.getCategoryIds()) {
            Category category = categoryService.findCategoryById(categoryId);
            if (category.isSubCategory()){
                categories.add(category);
            }else
                throw new BadRequestException("Category is not a sub category");
        }


        Product product = productBuilder.productCreateDtoToProduct(productCreateDto,categories,productType,generateLinkName(productCreateDto.getName()));
        Product saveProduct = productRepository.save(product);

        if (productCreateDto.getCoverImage() != null){
            ImageRequestDto imageRequestDto = new ImageRequestDto(productCreateDto.getCoverImage());
            CoverImage coverImage = coverImageService.save(imageRequestDto,saveProduct.getId());
            saveProduct.setCoverImage(coverImage);
        }

        if (productCreateDto.getProductImages() != null){
            List<ProductImage> productImages = new ArrayList<>();
            for (int i = 0;i<productCreateDto.getProductImages().length;i++){
                ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(productCreateDto.getProductImages()[i],i);
                ProductImage productImage = productImageService.save(productImageRequestDto, saveProduct.getId());
                productImages.add(productImage);
            }
            saveProduct.setProductImages(productImages);
            }
            return productBuilder.productToProductAdmindetailDto(productRepository.save(saveProduct));
    }


    public ProductAdminDetailDto updateSimpleProduct(@NotNullParam Integer productId,@NotNullParam ProductUpdateDto productUpdateDto){

        if (productUpdateDto.getName() == null || productUpdateDto.getName().isEmpty())
            throw new BadRequestException("Product name cannot be empty");

        if (productUpdateDto.getShortDescription() == null || productUpdateDto.getShortDescription().length() > 165)
            throw new BadRequestException("ShortDescription cannot be longer than 165 characters");

        if (productUpdateDto.getDescription() == null || productUpdateDto.getDescription().isEmpty())
            throw new BadRequestException("Product description cannot be empty");

        if ((productUpdateDto.getComparePrice() == null)
                || (productUpdateDto.getComparePrice().doubleValue() < 0)
                || productUpdateDto.getComparePrice().compareTo(productUpdateDto.getSalePrice()) > 0){
            throw new BadRequestException("ComparePrice cannot be greater than SalePrice");
        }

        if (productUpdateDto.getQuantity() < 0)
            throw new BadRequestException("Quantity cannot be less than 0");


        Product product = findProductById(productId);

        boolean isUpdated = false;  // Değişiklik olup olmadığını kontrol eden flag

        // Mevcut kategorileri al
        Set<Integer> existCategoryIds = product.getCategories().stream().map(Category::getId).collect(Collectors.toSet());
        Set<Category> existCategory = new HashSet<>(product.getCategories());

        // Yeni gelen kategori ID'lerini al
        Set<Integer> newCategoryIds = new HashSet<>(productUpdateDto.getCategoryIds());

        // Yeni eklenmesi gereken kategorileri bul
        Set<Category> categoriesToAdd = new HashSet<>();
        for (Integer categoryId : newCategoryIds) {
            if (!existCategoryIds.contains(categoryId)){
                Category category = categoryService.findCategoryById(categoryId);
                if (category.isSubCategory()){
                    categoriesToAdd.add(category);
                    isUpdated = true; // Kategori değişti
                } else {
                    throw new BadRequestException("Category is not a sub category");
                }
            }
        }

        // Çıkarılması gereken kategorileri bul
        Set<Category> categoriesToRemove = existCategory.stream()
                .filter(c -> !newCategoryIds.contains(c.getId()))
                .collect(Collectors.toSet());

        if (!categoriesToRemove.isEmpty() || !categoriesToAdd.isEmpty()) {
            product.getCategories().removeAll(categoriesToRemove);
            product.getCategories().addAll(categoriesToAdd);
            isUpdated = true;
        }

        if (!Objects.equals(product.getProductName(), productUpdateDto.getName())) {
            product.setProductName(productUpdateDto.getName());
            product.setProductLinkName(generateLinkName(product.getProductName()));
            isUpdated = true;
        }

        if (!Objects.equals(product.getSalePrice(), productUpdateDto.getSalePrice())) {
            product.setSalePrice(productUpdateDto.getSalePrice());
            isUpdated = true;
        }

        if (!Objects.equals(product.getComparePrice(), productUpdateDto.getComparePrice())) {
            product.setComparePrice(productUpdateDto.getComparePrice());
            isUpdated = true;
        }

        if (!Objects.equals(product.getBuyingPrice(), productUpdateDto.getBuyingPrice())) {
            product.setBuyingPrice(productUpdateDto.getBuyingPrice());
            isUpdated = true;
        }

        if (!Objects.equals(product.getQuantity(), productUpdateDto.getQuantity())) {
            product.setQuantity(productUpdateDto.getQuantity());
            isUpdated = true;
        }

        if (!Objects.equals(product.getShortDescription(), productUpdateDto.getShortDescription())) {
            product.setShortDescription(productUpdateDto.getShortDescription());
            isUpdated = true;
        }

        if (!Objects.equals(product.getProductDescription(), productUpdateDto.getDescription())) {
            product.setProductDescription(productUpdateDto.getDescription());
            isUpdated = true;
        }

        if (!Objects.equals(product.getProductType().name(), productUpdateDto.getProductType())) {
            ProductType productType = ProductType.valueOf(productUpdateDto.getProductType());
            product.setProductType(productType);
            isUpdated = true;
        }

        if (!Objects.equals(product.getPublished(), productUpdateDto.getPublished())) {
            product.setPublished(productUpdateDto.getPublished());
            isUpdated = true;
        }

        if (!Objects.equals(product.getDisableOutOfStock(), productUpdateDto.getDisableOutOfStock())) {
            product.setDisableOutOfStock(productUpdateDto.getDisableOutOfStock());
            isUpdated = true;
        }

        if (!isUpdated) {
            return productBuilder.productToProductAdmindetailDto(product); // Değişiklik yoksa, gereksiz `save` çağrısı yapma
        }
        return productBuilder.productToProductAdmindetailDto(productRepository.save(product));

    }


    @Transactional
    public String updateProductImage(@NotNullParam Integer productId, ProductImageUpdateDto productImageUpdateDto) {
        if(productImageUpdateDto == null)
            throw new BadRequestException("ProductImageUpdate "+ExceptionMessage.NOT_FOUND.getMessage());

        if (productId == null)
            throw new BadRequestException("ProductId Not Null");

        Product product = findProductById(productId);
        Set<ProductImage> currentImages = new HashSet<>(product.getProductImages()); // kopya

        boolean flag = false;

        if (productImageUpdateDto.getDeleteImages() != null){
            for (ProductImage productImage : currentImages) {
                for (int i = 0;i < productImageUpdateDto.getDeleteImages().size();i++){
                    if (productImageUpdateDto.getDeleteImages().get(i).equals(productImage.getId())){
                        deleteProductImage(productId, productImage.getId());
                        flag = true;
                    }
                }
            }
            if (!flag)
                throw new NotFoundException("ProductImage" + ExceptionMessage.NOT_FOUND.getMessage());
        }


        Set<ProductImage> newProductImages = new HashSet<>();

        if (productImageUpdateDto.getNewImages() != null){
            for (MultipartFile newImage : productImageUpdateDto.getNewImages()) {
                System.out.println("name: "+newImage.getName());
                ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(newImage,0);
                ProductImage save = productImageService.save(productImageRequestDto, productId);
                newProductImages.add(save);
            }
        }else
            System.out.println("updateproductImage ,image not found");

        product.getProductImages().addAll(newProductImages);
        productRepository.save(product);
        return "updated ProductImage";

    }

    @Transactional
    public String deleteProductImage(@NotNullParam Integer productId,@NotNullParam Integer productImageId) {

        System.out.println("deleteProductImage" + productImageId + " prod: " + productId);
        Product product = findProductById(productId);

        ProductImage targetImage = null;
        for (ProductImage productImage : product.getProductImages()) {
            if (productImage.getId() == productImageId) {
                targetImage = productImage;
                break;
            }
        }

        if (targetImage != null) {
            product.getProductImages().remove(targetImage);
            productImageService.delete(targetImage.getId());
        }else
            throw new NotFoundException("ProductImage" + ExceptionMessage.NOT_FOUND.getMessage());

        productRepository.save(product);
        return "deleted ProductImage";
    }


    @Transactional
    public String deleteCoverImage(@NotNullParam Integer productId) {

        Product product = findProductById(productId);
        if (product.getCoverImage() != null) {
            coverImageService.delete(product.getCoverImage().getId());
            product.setCoverImage(null);
            productRepository.save(product);
            return "deleted CoverImage";
        }else
            throw new NotFoundException("Cover image" + ExceptionMessage.NOT_FOUND.getMessage());
    }

    @Transactional
    public ImageDetailDto updateCoverImage(@NotNullParam Integer productId,@NotNullParam MultipartFile coverImage) {

        Product product = findProductById(productId);
        if (product.getCoverImage() != null) {
            coverImageService.delete(product.getCoverImage().getId());
            product.setCoverImage(null);
            productRepository.save(product);
        }

        ImageRequestDto imageRequestDto = new ImageRequestDto(coverImage);
        CoverImage newCoverImage = coverImageService.save(imageRequestDto,product.getId());
        product.setCoverImage(newCoverImage);
        productRepository.save(product);
        return new ImageDetailDto(newCoverImage.getId(), newCoverImage.getName(),newCoverImage.getResolution(),newCoverImage.getName(), newCoverImage.getUrl(), 0);

    }


    public ProductAdminDetailDto deleteProduct(@NotNullParam Integer productId) {

        Product product = findProductById(productId);
        product.setDeleted(true);
        return productBuilder.productToProductAdmindetailDto(productRepository.save(product));

    }

    public ProductAttribute createProductAttribute(Integer productId,Integer attributeId) {
        Attribute attribute = attributeService.findAttributeById(attributeId);
        Product product = findProductById(productId);

        ProductAttribute productAttribute = new ProductAttribute(product,attribute);
        return productAttributeRepository.save(productAttribute);
    }

    // ------------- list product ------------------

    public Product findProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public boolean isExistProductById(Integer productId) {
        return productRepository.existsById(productId);
    }

    public List<ProductAdminDetailDto> filterProductsByCategory(@NotNullParam ProductFilterRequest filterRequest,@NotNullParam Integer page,@NotNullParam Integer size) {
        Sort sort = Sort.unsorted();
        if (filterRequest.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(filterRequest.getSortDirection()), filterRequest.getSortBy());
        }
        Category category = categoryService.findCategoryById(filterRequest.getCategoryId());
        Set<Integer> subCategories = categoryService.getLeafCategories(category).stream().map(Category::getId).collect(Collectors.toSet());

        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Product> specification = filterProducts(subCategories,filterRequest.getMinPrice(),filterRequest.getMaxPrice());
        return productRepository.findAll(specification,pageable).stream().map(productBuilder::productToProductAdmindetailDto).collect(Collectors.toList());
    }

    public Set<ProductSmallDto> filterProductsByCategorySmall(@NotNullParam ProductFilterRequest filterRequest,@NotNullParam Integer page,@NotNullParam Integer size) {
        Sort sort = Sort.unsorted();
        if (filterRequest.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(filterRequest.getSortDirection()), filterRequest.getSortBy());
        }


        Set<Integer> subCategories = null;
        if (filterRequest.getCategoryId() != null) {
            Category category = categoryService.findCategoryById(filterRequest.getCategoryId());
            subCategories = categoryService.getLeafCategories(category).stream().map(Category::getId).collect(Collectors.toSet());
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Product> specification = filterProducts(subCategories,filterRequest.getMinPrice(),filterRequest.getMaxPrice());
        return productRepository.findAll(specification,pageable).stream().map(productBuilder::productToProductSmallDto).collect(Collectors.toSet());
    }

    public Specification<Product> filterProducts(Set<Integer> categoriesId, BigDecimal minPrice, BigDecimal maxPrice) {
        return Specification
                .where(hasCategories(categoriesId))
                .and(hasMinPrice(minPrice))
                .and(hasMaxPrice(maxPrice))
                .and(isDisableOutOfStock(true))
                .and(hasMinQuantity(0))
                .and(hasProductType(ProductType.SIMPLE))
                .and(isPublished(true))
                .and(isDeleted(false));
    }

    public Product findProductByLinkName(String linkName) {
        return productRepository.findByProductLinkName(linkName).orElse(null);
    }

    public Specification<Product> hasLinkName(String linkName) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                linkName == null ? null : cb.equal(root.get("productLinkName"), linkName);
    }

    public Specification<Product> hasCategories(Set<Integer> categoryIds) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (categoryIds == null || categoryIds.isEmpty()) return null;
            Join<Product, Category> categoryJoin = root.join("categories", JoinType.INNER);
            return categoryJoin.get("id").in(categoryIds);
        };
    }

    public Specification<Product> hasProductType(ProductType productType) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                productType == null ? null : cb.equal(root.get("productType"), productType);
    }

    public Specification<Product> hasMinPrice(BigDecimal minPrice) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("comparePrice"), minPrice);
    }

    public Specification<Product> hasMinQuantity(Integer minQuantity) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                minQuantity == null ? null : cb.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
    }

    public Specification<Product> hasMaxPrice(BigDecimal maxPrice) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("comparePrice"), maxPrice);
    }

    public Specification<Product> isDisableOutOfStock(Boolean disableOutOfStock) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("disableOutOfStock"), disableOutOfStock);
    }

    public Specification<Product> isDeleted(Boolean isDeleted) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("isDeleted"), isDeleted);
    }

    public Specification<Product> isPublished(Boolean isPublished) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("published"), isPublished);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }


    public ProductDetailDto findProductDetail(@NotNullParam String linkName) {
        Product product = productRepository.findByProductLinkName(linkName).orElseThrow(()-> new NotFoundException("Product "+ExceptionMessage.NOT_FOUND.getMessage()));
        return productBuilder.productToProductDetailDto(product);
    }

    public Product findProductDetailAdmin(@NotNullParam String linkName) {
        return productRepository.findByProductLinkName(linkName).orElseThrow(()-> new NotFoundException("Product "+ExceptionMessage.NOT_FOUND.getMessage()));
    }


    private String generateLinkName(String name) {
        if (name == null) return "";

        String processedName = name.trim().toLowerCase()
                .replace("ç", "c")
                .replace("ğ", "g")
                .replace("ı", "i")
                .replace("ö", "o")
                .replace("ş", "s")
                .replace("ü", "u")
                .replaceAll("\\s+", "-");

        String finalLinkName = processedName;
        int counter = 1;

        // Zaten linkName kontrolünü yapan metodunu kullanalım
        while (findProductByLinkName(finalLinkName) != null) {
            finalLinkName = processedName + "-" + counter;
            counter++;
        }

        return finalLinkName;
    }
}
