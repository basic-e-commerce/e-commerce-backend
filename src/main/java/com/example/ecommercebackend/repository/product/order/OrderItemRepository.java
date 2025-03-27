package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.product.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
