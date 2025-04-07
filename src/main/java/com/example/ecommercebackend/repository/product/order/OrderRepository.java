package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderCode(String orderCode);
    Optional<Order> findByPaymentsContaining(Payment payment);
}
