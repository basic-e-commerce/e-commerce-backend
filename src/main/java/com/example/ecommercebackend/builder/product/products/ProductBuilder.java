package com.example.ecommercebackend.builder.product.products;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.product.products.ProductCreateDto;
import com.example.ecommercebackend.dto.product.products.ProductDetailDto;
import com.example.ecommercebackend.dto.product.products.ProductSmallDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.ProductType;
import com.example.ecommercebackend.entity.user.Admin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ProductBuilder {

    public Product productCreateDtoToProduct(ProductCreateDto productCreateDto, Set<Category> categories, ProductType productType, Admin create, Admin update) {
        return new Product(
                productCreateDto.getName(),
                productCreateDto.getSalePrice(),
                productCreateDto.getComparePrice(),
                productCreateDto.getBuyingPrice(),
                productCreateDto.getQuantity(),
                productCreateDto.getShortDescription(),
                categories,
                productCreateDto.getProductDescription(),
                productType,
                productCreateDto.getPublished(),
                productCreateDto.getDisableOutOfStock(),
                create,
                update
        );
    }

    public ProductSmallDto productToProductSmallDto(Product product) {
        String coverUrl = "";
        if (product.getCoverImage() != null) {
            coverUrl = product.getCoverImage().getUrl();
        }

        return new ProductSmallDto(
                product.getId(),
                product.getProductName(),
                product.getProductLinkName(),
                product.getShortDescription(),
                product.getSalePrice(),
                product.getComparePrice(),
                coverUrl,
                product.getQuantity()
        );
    }

    public ProductDetailDto productToProductDetailDto(Product product) {
        ImageDetailDto coverImage = null;
        if (product.getCoverImage() != null) {
            coverImage = new ImageDetailDto(product.getCoverImage().getId(),product.getCoverImage().getUrl(),0);
        }

        List<ImageDetailDto> productImages = new ArrayList<>();
        if (product.getProductImages() != null) {
            for (int i = 0; i < product.getProductImages().size(); i++) {
                productImages.add(new ImageDetailDto(product.getProductImages().get(i).getId(),product.getProductImages().get(i).getUrl(),0));
            }
        }
        return new ProductDetailDto(
                product.getId(),
                product.getProductName(),
                product.getProductLinkName(),
                product.getShortDescription(),
                product.getProductDescription(),
                product.getSalePrice(),
                product.getComparePrice(),
                coverImage,
                productImages,
                product.getQuantity()/2
        );

    }

}
