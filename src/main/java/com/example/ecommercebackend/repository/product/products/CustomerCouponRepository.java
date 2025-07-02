package com.example.ecommercebackend.repository.product.products;

import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerCouponRepository extends JpaRepository<CustomerCoupon, Integer>, JpaSpecificationExecutor<CustomerCoupon> {
}
