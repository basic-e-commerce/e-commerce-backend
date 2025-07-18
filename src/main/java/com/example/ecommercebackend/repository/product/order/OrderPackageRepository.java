package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderPackageRepository extends JpaRepository<OrderPackage, Integer>, JpaSpecificationExecutor<OrderPackage> {
}
