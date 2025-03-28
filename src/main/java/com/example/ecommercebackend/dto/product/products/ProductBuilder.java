package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.ProductType;
import com.example.ecommercebackend.entity.user.Admin;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductBuilder {

    public Product productCreateDtoToProduct(ProductCreateDto productCreateDto, Set<Category> categories, ProductType productType, Admin create,Admin update) {
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

}
