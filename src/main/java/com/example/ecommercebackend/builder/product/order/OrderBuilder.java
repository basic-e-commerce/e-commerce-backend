package com.example.ecommercebackend.builder.product.order;

import com.example.ecommercebackend.dto.product.invoice.CorporateInvoiceResponseDto;
import com.example.ecommercebackend.dto.product.invoice.InvoiceResponseDto;
import com.example.ecommercebackend.dto.product.order.AddressOrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderResponseDto;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.invoice.CorporateInvoice;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.entity.product.order.Order;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

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

        OrderDetailDto orderDetailDto = new OrderDetailDto(
                order.getId(),
                order.getOrderCode(),
                order.getUser().getFirstName(),
                order.getUser().getLastName(),
                order.getTotalPrice(),
                new AddressOrderDetailDto(
                        order.getInvoice().getFirstName(),
                        order.getInvoice().getLastName(),
                        order.getInvoice().getUsername(),
                        order.getInvoice().getCountryName(),
                        order.getInvoice().getCity(),
                        order.getInvoice().getPostalCode(),
                        order.getInvoice().getPhoneNumber(),
                        order.getInvoice().getAddressLine1()
                ),
                order.getOrderItems().stream().map(orderItem -> {

                    String coverImage = "";
                    if (orderItem.getProduct().getCoverImage() != null) {
                        coverImage = orderItem.getProduct().getCoverImage().getUrl();
                    }
                    return new OrderItemResponseDto(orderItem.getProduct().getId(), orderItem.getProduct().getProductName(), orderItem.getQuantity(), coverImage);
                }).toList(),
                installment,
                order.getOrderStatus().getStatus().name()
        );
        InvoiceResponseDto invoiceResponseDto = new InvoiceResponseDto(
                order.getInvoice().getId(),
                order.getInvoice().getInvoiceType().name(),
                order.getInvoice().getFirstName(),
                order.getInvoice().getLastName(),
                order.getInvoice().getUsername(),
                order.getInvoice().getCountryName(),
                order.getInvoice().getCity(),
                order.getInvoice().getAddressLine1(),
                order.getInvoice().getPostalCode(),
                order.getInvoice().getPhoneNumber(),
                "oluşturulma tarihi"
                );

        Class<?> clazz = Hibernate.getClass(order.getInvoice());
        System.out.println("asd: "+clazz.getSimpleName());

        if (order.getInvoice() instanceof CorporateInvoice corporateInvoice) {
            System.out.println("corporateInvoice bu --------------------------------------");
            CorporateInvoiceResponseDto corporateInvoiceResponseDto = new CorporateInvoiceResponseDto(
                    corporateInvoice.getCompanyName(),
                    corporateInvoice.getTaxNumber(),
                    corporateInvoice.getTaxOffice()
            );
            invoiceResponseDto.setCorporateInvoice(corporateInvoiceResponseDto);
        }
        orderDetailDto.setInvoiceResponseDto(invoiceResponseDto);
        return orderDetailDto;
    }
}
