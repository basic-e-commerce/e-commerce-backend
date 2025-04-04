package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.repository.product.order.OrderStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public OrderStatus createOrderStatus(OrderStatus.Status status, OrderStatus.Privacy privacy, OrderStatus.Color color) {
        OrderStatus orderStatus = new OrderStatus(status,privacy,color);
        return orderStatusRepository.save(orderStatus);
    }
}
