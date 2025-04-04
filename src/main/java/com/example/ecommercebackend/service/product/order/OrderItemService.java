package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.repository.product.order.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Set<OrderItem> saveOrderItems(Set<OrderItem> orderItem) {
        Set<OrderItem> savedOrderItems = new HashSet<>();
        for (OrderItem orderItem1 : orderItem) {
            savedOrderItems.add(orderItemRepository.save(orderItem1));
        }
        return savedOrderItems;
    }
}
