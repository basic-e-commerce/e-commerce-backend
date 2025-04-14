package com.example.ecommercebackend.builder.product.order;

import com.example.ecommercebackend.dto.product.order.AddressOrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderResponseDto;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.entity.product.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderBuilder {

    public OrderResponseDto orderToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderCode(),
                order.getTotalPrice(),
                order.getOrderItems().stream().map(x-> {
                    return new OrderItemResponseDto(x.getProduct().getId(),x.getProduct().getProductName(),x.getQuantity());
                }).toList()
        );
    }

    public OrderDetailDto orderToOrderDetailDto(Order order) {
        return new OrderDetailDto(
                order.getId(),
                order.getOrderCode(),
                order.getUser().getFirstName(),
                order.getUser().getLastName(),
                order.getTotalPrice(),
                new AddressOrderDetailDto(
                        order.getFirstName(),
                        order.getLastName(),
                        order.getUsername(),
                        order.getCountry(),
                        order.getCity(),
                        order.getPostalCode(),
                        order.getPhoneNumber(),
                        order.getAddressLine1()
                ),
                order.getOrderItems().stream().map(orderItem -> {
                    return new OrderItemResponseDto(orderItem.getProduct().getId(),orderItem.getProduct().getProductName(),orderItem.getQuantity());
                }).toList()
        );
    }
}
