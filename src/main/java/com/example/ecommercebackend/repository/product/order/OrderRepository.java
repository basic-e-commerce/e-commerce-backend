package com.example.ecommercebackend.repository.product.order;

import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> , JpaSpecificationExecutor<Order> {
    Optional<Order> findByOrderCode(String orderCode);
    Optional<Order> findByPaymentsContaining(Payment payment);
    @Query("SELECT SUM(o.totalPrice) FROM Order o JOIN o.orderStatus s WHERE s.status = :status AND o.createdAt BETWEEN :start AND :end")
    BigDecimal findSuccessTotalPriceBetweenDates(@Param("status") OrderStatus.Status status, @Param("start") Instant startDate, @Param("end") Instant endDate);

}
