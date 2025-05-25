package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.entity.product.products.Product;

public class ProductQuantityDto {
    private Product product;
    private int quantity;

    public ProductQuantityDto(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
