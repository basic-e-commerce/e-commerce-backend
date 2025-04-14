package com.example.ecommercebackend.builder.product.sell;

import com.example.ecommercebackend.dto.product.sell.ProductSellDto;
import com.example.ecommercebackend.entity.product.products.Sell;
import org.springframework.stereotype.Component;

@Component
public class SellBuilder {

    public ProductSellDto sellToProductSellDto(Sell sell) {
        return new ProductSellDto(
                sell.getProduct().getId(),
                sell.getProduct().getProductName(),
                sell.getPrice(),
                sell.getQuantity(),
                sell.getProduct().getCoverImage().getUrl()
        );
    }
}
