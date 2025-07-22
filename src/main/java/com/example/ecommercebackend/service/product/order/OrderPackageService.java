package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.dto.product.order.OrderPackageResponseDto;
import com.example.ecommercebackend.dto.product.order.WebhookTrackUpdatedData;
import com.example.ecommercebackend.dto.product.order.WebhookTrackUpdatedPayload;
import com.example.ecommercebackend.dto.product.order.WebhookTrackingUpdatedStatus;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.order.OrderPackageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class OrderPackageService {
    private final OrderPackageRepository orderPackageRepository;

    public OrderPackageService(OrderPackageRepository orderPackageRepository) {
        this.orderPackageRepository = orderPackageRepository;
    }

    public OrderPackage createOrderPackage(OrderPackage orderPackage) {
        return orderPackageRepository.save(orderPackage);
    }

    public OrderPackage findById(Integer id) {
        return orderPackageRepository.findById(id).orElseThrow(()-> new NotFoundException("Order Package Bulunamadı"));
    }

    public void save(OrderPackage orderPackage) {
        orderPackageRepository.save(orderPackage);
    }

    public OrderPackageResponseDto getDetails(Integer id) {
        OrderPackage orderPackage = orderPackageRepository.findById(id).orElseThrow(() -> new NotFoundException("Sipariş paketi bulunamadı"));

        return new OrderPackageResponseDto(
                orderPackage.getId(),
                orderPackage.getOrderItems().stream().map(x->{
                    return new OrderItemResponseDto(
                            x.getProduct().getId(),
                            x.getProduct().getProductName(),
                            x.getQuantity(),
                            x.getProduct().getCoverImage().getUrl()
                    );
                }).collect(Collectors.toSet()),
                orderPackage.getShipmentId(),
                orderPackage.getStatusCode().name(),
                orderPackage.getCargoId(),
                orderPackage.getCargoCompany().name(),
                orderPackage.getCargoStatus().getValue(),
                orderPackage.getLocation(),
                orderPackage.getUpdateAt().atZone(ZoneId.of("Istabul/Europe")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                orderPackage.getCanceled()
        );
    }

    public OrderPackage getDetailsNull(Integer id) {
        return orderPackageRepository.findById(id).orElse(null);
    }

    public String trackUpdate(WebhookTrackUpdatedPayload webhookTrackUpdatedPayload) {
        if (webhookTrackUpdatedPayload == null) {
            System.out.println("Webhook payload is null");
        }

        System.out.println("==== WEBHOOK RECEIVED ====");
        System.out.println("Event: " + webhookTrackUpdatedPayload.getEvent());
        System.out.println("Metadata: " + webhookTrackUpdatedPayload.getMetadata());

        WebhookTrackUpdatedData data = webhookTrackUpdatedPayload.getData();
        if (data != null) {
            System.out.println("Data ID: " + data.getId());
            System.out.println("Tracking Number: " + data.getTrackingNumber());
            System.out.println("Tracking URL: " + data.getTrackingUrl());

            WebhookTrackingUpdatedStatus trackingStatus = data.getTrackingStatus();
            if (trackingStatus != null) {
                System.out.println("Tracking Status Code: " + trackingStatus.getTrackingStatusCode());
                System.out.println("Tracking Sub Status Code: " + trackingStatus.getTrackingSubStatusCode());
                System.out.println("Status Details: " + trackingStatus.getStatusDetails());
                System.out.println("Location Name: " + trackingStatus.getLocationName());
            } else {
                System.out.println("Tracking Status: null");
            }
        } else {
            System.out.println("Data: null");
        }

        OrderPackage orderPackage = orderPackageRepository.findByCargoId(webhookTrackUpdatedPayload.getData().getId()).orElseThrow(()-> new NotFoundException("ORder PAckage Bulunamadı"));
        orderPackage.setCargoStatus(OrderPackage.CargoStatus.valueOf(webhookTrackUpdatedPayload.getData().getTrackingStatus().getTrackingSubStatusCode()));
        orderPackage.setStatusCode(OrderPackage.StatusCode.valueOf(webhookTrackUpdatedPayload.getData().getTrackingStatus().getTrackingStatusCode()));
        orderPackage.setUpdateAt(Instant.parse(webhookTrackUpdatedPayload.getData().getTrackingStatus().getUpdateAt()));
        orderPackage.setLocation(webhookTrackUpdatedPayload.getData().getTrackingStatus().getLocationName());
        orderPackageRepository.save(orderPackage);
        return "Sipariş paketi güncellendi";
    }
}
