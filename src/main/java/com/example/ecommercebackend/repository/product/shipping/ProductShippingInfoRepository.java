package com.example.ecommercebackend.repository.product.shipping;

import com.example.ecommercebackend.entity.product.shipping.ProductShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductShippingInfoRepository extends JpaRepository<ProductShippingInfo, Integer> {
}
