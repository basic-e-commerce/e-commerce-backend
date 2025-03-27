package com.example.ecommercebackend.repository.product.shipping;

import com.example.ecommercebackend.entity.product.shipping.ShippingRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRateRepository extends JpaRepository<ShippingRate, Integer> {
}
