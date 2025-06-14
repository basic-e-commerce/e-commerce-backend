package com.example.ecommercebackend.builder.product.order;

import com.example.ecommercebackend.dto.product.order.AddressOrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderResponseDto;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderBuilder {

    public OrderResponseDto orderToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderCode(),
                order.getTotalPrice(),
                order.getOrderItems().stream().map(x-> {
                    String coverImage = "";
                    if (x.getProduct().getCoverImage() != null) {
                        coverImage = x.getProduct().getCoverImage().getUrl();
                    }
                    return new OrderItemResponseDto(x.getProduct().getId(),x.getProduct().getProductName(),x.getQuantity(),coverImage);
                }).toList()
        );
    }

    public OrderDetailDto orderToOrderDetailDto(Order order) {
        int installment = 1;
        for (Payment payment : order.getPayments()) {
            if (payment.getPaymentStatus() == Payment.PaymentStatus.SUCCESS){
                installment = payment.getInstallment();
            }
        }
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

                    String coverImage = "";
                    if (orderItem.getProduct().getCoverImage() != null) {
                        coverImage = orderItem.getProduct().getCoverImage().getUrl();
                    }
                    return new OrderItemResponseDto(orderItem.getProduct().getId(),orderItem.getProduct().getProductName(),orderItem.getQuantity(),coverImage);
                }).toList(),
                installment,
                order.getOrderStatus().getStatus().name()
        );
    }
}
