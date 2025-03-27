package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.product.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
