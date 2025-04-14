package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> , JpaSpecificationExecutor<Order> {
    Optional<Order> findByOrderCode(String orderCode);
    Optional<Order> findByPaymentsContaining(Payment payment);
}
