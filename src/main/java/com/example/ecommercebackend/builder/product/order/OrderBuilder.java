package com.example.ecommercebackend.builder.product.order;

import com.example.ecommercebackend.dto.product.invoice.CorporateInvoiceResponseDto;
import com.example.ecommercebackend.dto.product.invoice.InvoiceResponseDto;
import com.example.ecommercebackend.dto.product.order.*;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.invoice.CorporateInvoice;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.entity.product.order.Order;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                    return new OrderItemResponseDto(x.getProduct().getId(),x.getProduct().getProductName(),x.getQuantity(),coverImage);
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
                    return new OrderItemResponseDto(orderItem.getProduct().getId(), orderItem.getProduct().getProductName(), orderItem.getQuantity(), coverImage);
                }).toList(),
                installment,
                new OrderStatusResponse(
                        order.getOrderStatus().getId(),
                        order.getOrderStatus().getStatus().getValue(),
                        new ArrayList<>(order.getOrderStatus().getOrderPackages()).stream().sorted().map(x-> {
                            return new OrderPackageResponseDto(
                                    x.getId(),
                                    x.getOrderItems().stream().map(y->{
                                        return new OrderItemResponseDto(
                                                y.getProduct().getId(),
                                                y.getProduct().getProductName(),
                                                y.getProduct().getQuantity(),
                                                y.getProduct().getCoverImage().getUrl()
                                        );}
                                    ).collect(Collectors.toSet()),
                                    x.getShipmentId(),
                                    x.getStatusCode().name(),
                                    x.getCargoId(),
                                    x.getCargoCompany().name(),
                                    x.getCargoStatus().getValue(),
                                    x.getLocation(),
                                    x.getUpdateAt()
                                            .atZone(ZoneId.of("Europe/Istanbul"))
                                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                    x.getCanceled()
                            );
                        }).toList(),
                        order.getOrderStatus().getColor().name(),
                        order.getOrderStatus().getCreatedAt().atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
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
