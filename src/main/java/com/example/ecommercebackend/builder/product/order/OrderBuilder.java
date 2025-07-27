package com.example.ecommercebackend.builder.product.order;

import com.example.ecommercebackend.dto.product.invoice.CorporateInvoiceResponseDto;
import com.example.ecommercebackend.dto.product.invoice.InvoiceResponseDto;
import com.example.ecommercebackend.dto.product.order.*;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.invoice.CorporateInvoice;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
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
                    return new OrderItemResponseDto(
                            x.getProduct().getId(),
                            x.getProduct().getProductName(),
                            x.getQuantity(),
                            coverImage,
                            x.getDiscountPrice(),
                            x.getId());
                }).toList()
        );
    }

    public OrderDetailDto orderToOrderDetailDto(Order order) {
        int installment = 1;

            if (order.getPayments().getPaymentStatus() == Payment.PaymentStatus.SUCCESS){
                installment = order.getPayments().getInstallment();
            }


        OrderDetailDto orderDetailDto = new OrderDetailDto(
                order.getId(),
                order.getOrderCode(),
                order.getUser().getFirstName(),
                order.getUser().getLastName(),
                order.getTotalPrice(),
                order.getCustomerPrice(),
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
                    return new OrderItemResponseDto(orderItem.getProduct().getId(), orderItem.getProduct().getProductName(), orderItem.getQuantity(), coverImage,orderItem.getDiscountPrice(),orderItem.getId());
                }).toList(),
                order.getRefundOrderItems().stream().map(orderItem -> {

                    String coverImage = "";
                    if (orderItem.getProduct().getCoverImage() != null) {
                        coverImage = orderItem.getProduct().getCoverImage().getUrl();
                    }
                    return new OrderItemResponseDto(orderItem.getProduct().getId(), orderItem.getProduct().getProductName(), orderItem.getQuantity(), coverImage,orderItem.getDiscountPrice(),orderItem.getId());
                }).toList(),
                installment,
                new OrderStatusResponse(
                        order.getOrderStatus() != null ? order.getOrderStatus().getId() : null,
                        order.getOrderStatus() != null && order.getOrderStatus().getStatus() != null
                                ? order.getOrderStatus().getStatus().getValue() : null,
                        Optional.ofNullable(order.getOrderStatus())
                                .map(OrderStatus::getOrderPackages)
                                .orElse(Collections.emptyList())
                                .stream()
                                .sorted(Comparator.comparing(OrderPackage::getCreateAt))
                                .map(x -> new OrderPackageResponseDto(
                                        x.getId(),
                                        Optional.ofNullable(x.getOrderItems())
                                                .orElse(Collections.emptySet())
                                                .stream()
                                                .map(y -> new OrderItemResponseDto(
                                                        y.getProduct() != null ? y.getProduct().getId() : null,
                                                        y.getProduct() != null ? y.getProduct().getProductName() : null,
                                                        y.getQuantity() != null ? y.getQuantity() : null,
                                                        y.getProduct() != null && y.getProduct().getCoverImage() != null
                                                                ? y.getProduct().getCoverImage().getUrl() : null,
                                                        y.getDiscountPrice() != null ? y.getDiscountPrice() : null,
                                                        y.getId() != null ? y.getId() : null
                                                )).collect(Collectors.toSet()),
                                        x.getShipmentId(),
                                        x.getStatusCode() != null ? x.getStatusCode().name() : null,
                                        x.getCargoId(),
                                        x.getCargoCompany() != null ? x.getCargoCompany().name() : null,
                                        x.getCargoStatus() != null ? x.getCargoStatus().getValue() : null,
                                        x.getLocation(),
                                        x.getUpdateAt() != null
                                                ? x.getUpdateAt()
                                                .atZone(ZoneId.of("Europe/Istanbul"))
                                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                                : null,
                                        x.getCanceled()
                                )).toList(),
                        order.getOrderStatus() != null && order.getOrderStatus().getColor() != null
                                ? order.getOrderStatus().getColor().name() : null,
                        order.getOrderStatus() != null && order.getOrderStatus().getCreatedAt() != null
                                ? order.getOrderStatus().getCreatedAt()
                                .atZone(ZoneId.of("Europe/Istanbul"))
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                : null
                ),
                order.getCreatedAt().atZone(ZoneId.of("Europe/Istanbul")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
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

        Invoice invoice = (Invoice) Hibernate.unproxy(order.getInvoice());

        if (invoice instanceof CorporateInvoice corporateInvoice) {
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
