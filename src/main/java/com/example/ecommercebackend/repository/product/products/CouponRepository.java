package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> , JpaSpecificationExecutor<Coupon> {
    Boolean existsByCode(String code);
}
