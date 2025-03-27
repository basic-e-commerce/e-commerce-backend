package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.product.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
}
