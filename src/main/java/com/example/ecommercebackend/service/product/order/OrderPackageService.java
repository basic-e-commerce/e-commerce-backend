package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.dto.product.order.OrderPackageResponseDto;
import com.example.ecommercebackend.dto.product.order.WebhookTrackUpdatedData;
import com.example.ecommercebackend.dto.product.order.WebhookTrackUpdatedPayload;
import com.example.ecommercebackend.dto.product.order.WebhookTrackingUpdatedStatus;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemRefundDto;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.dto.product.shipping.OrderPackageUpdateDto;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.order.OrderPackageRepository;
import com.example.ecommercebackend.repository.product.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderPackageService {
    private final OrderPackageRepository orderPackageRepository;
    private final OrderRepository orderRepository;

    public OrderPackageService(OrderPackageRepository orderPackageRepository, OrderRepository orderRepository) {
        this.orderPackageRepository = orderPackageRepository;
        this.orderRepository = orderRepository;
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
                            x.getProduct().getCoverImage().getUrl(),
                            x.getPrice(),
                            x.getDiscountPrice(),
                            x.getId()
                    );
                }).collect(Collectors.toSet()),
                orderPackage.getShipmentId(),
                orderPackage.getOrderPackageStatusCode().name(),
                orderPackage.getCargoId(),
                orderPackage.getCargoCompany().name(),
                orderPackage.getCargoStatus().getValue(),
                orderPackage.getLocation(),
                orderPackage.getUpdateAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                orderPackage.getCanceled(),
                orderPackage.getManuel()
        );
    }

    public OrderPackage getDetailsNull(Integer id) {
        return orderPackageRepository.findById(id).orElse(null);
    }

    /*
    başlangıçta orderstatus = approve
    teslim edilirse sadece orderPackagestatus delivered olacak

    iade oldugu vakit orderpackagestatus = iade
    hemde orderstaus iadeye dönecek
    orderItem  olarakta eklenmesi gerekiyor
    refundOrderpackagese eklenmesi gerekiyor.

    iptal ise orderpackage iptal
    hemde order status değeri iptal
 */

    public String orderPackageUpdate(Integer orderPackageId, OrderPackageUpdateDto orderPackageUpdateDto){
        OrderPackage orderPackage = findById(orderPackageId);
        if (!orderPackage.getManuel())
            throw new BadRequestException("Manuel olmayan kargo güncellenemez. Sistem otomatik güncelleyecektir!");

        Order order = orderRepository.findByOrderCode(orderPackageUpdateDto.getOrderCode()).orElseThrow(()-> new NotFoundException("Sipariş bulunamadı!"));

//        if (orderPackageUpdateDto.getOrderPackageStatusCode().equals(OrderPackage.OrderPackageStatusCode.RETURNED)){
////
////            Set<OrderItem> orderItems = order.getOrderItems();
////            Set<OrderItem> refundOrderItems = order.getRefundOrderItems();
////            Set<OrderItem> refundItems = new HashSet<>();
////
////            for (OrderItemRefundDto refundDto : orderPackageUpdateDto.getOrderItemRefundDtos()) {
////
////                Integer refundOrderItemId = refundDto.orderItemId();
////                int refundProductId = refundDto.productId();
////
////                System.out.println("İade talebi kontrol ediliyor -> OrderItemId: " + refundOrderItemId + ", ProductId: " + refundProductId);
////
////                OrderItem matchingOrderItem = null;
////
////                for (OrderItem orderItem : orderItems) {
////                    System.out.println("Kontrol edilen OrderItem -> Id: " + orderItem.getId()
////                            + ", ProductId: " + orderItem.getProduct().getId());
////
////                    if (orderItem.getId().equals(refundOrderItemId) &&
////                            orderItem.getProduct().getId() == refundProductId) {
////                        matchingOrderItem = orderItem;
////                        break;
////                    }
////                }
////
////                OrderItem matchingRefundOrderItem = null;
////
////                for (OrderItem refundOrderItem : refundOrderItems) {
////                    System.out.println("Kontrol edilen OrderItem -> Id: " + refundOrderItem.getId()
////                            + ", ProductId: " + refundOrderItem.getProduct().getId());
////
////                    if (refundOrderItem.getId().equals(refundOrderItemId) &&
////                            refundOrderItem.getProduct().getId() == refundProductId) {
////                        matchingRefundOrderItem = refundOrderItem;
////                        break;
////                    }
////                }
////
////                if (matchingOrderItem == null) {
////                    System.err.println("UYARI: Eşleşen OrderItem bulunamadı! İstenen -> OrderItemId: "
////                            + refundOrderItemId + ", ProductId: " + refundProductId);
////                    throw new BadRequestException(
////                            "Siparişte bu ürün kalemi bulunmamaktadır! OrderItemId: " +
////                                    refundOrderItemId + ", ProductId: " + refundProductId
////                    );
////
////                }else{
////                    if (matchingRefundOrderItem != null) {
////                        if ((matchingOrderItem.getQuantity()-refundDto.quantity()-matchingRefundOrderItem.getQuantity()) < 0)
////                            throw new BadRequestException("Siparişte verilen miktardan fazla iade edemezsiniz!");
////                    }
////                }
////
////                System.out.println("Eşleşen OrderItem bulundu -> Id: " + matchingOrderItem.getId()
////                        + ", ProductId: " + matchingOrderItem.getProduct().getId());
////
////                if (matchingRefundOrderItem != null) {
////                    matchingRefundOrderItem.setQuantity(matchingRefundOrderItem.getQuantity()+refundDto.quantity());
////                    refundItems.add(matchingRefundOrderItem);
////                }else{
////                    OrderItem refundOrderItem = new OrderItem();
////                    refundOrderItem.setProduct(matchingOrderItem.getProduct());
////                    refundOrderItem.setQuantity(refundDto.quantity());
////                    refundOrderItem.setRefund(true);
////                    refundOrderItem.setPrice(matchingOrderItem.getPrice()
////                            .divide(BigDecimal.valueOf(matchingOrderItem.getQuantity()), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(refundDto.quantity())));
////                    refundOrderItem.setDiscountPrice(matchingOrderItem.getDiscountPrice()
////                            .divide(BigDecimal.valueOf(matchingOrderItem.getQuantity()), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(refundDto.quantity())));
////
////                    refundItems.add(refundOrderItem);
////                }
////
////            }
////
////            if (refundItems.isEmpty()) {
////                throw new BadRequestException("İade Edilecek ürün bulunamamıştır");
////            }
////
////            order.setRefundOrderItems(refundItems);
////
////            OrderPackage refundOrderPackage = new OrderPackage();
////            refundOrderPackage.setManuel(true);
////            refundOrderPackage.setLength(orderPackage.getLength());
////            refundOrderPackage.setWeight(orderPackage.getWeight());
////            refundOrderPackage.setWidth(orderPackage.getWidth());
////            refundOrderPackage.setHeight(orderPackage.getHeight());
////            refundOrderPackage.setDistanceUnit(orderPackage.getDistanceUnit());
////            refundOrderPackage.setMassUnit(orderPackage.getMassUnit());
////            refundOrderPackage.setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode.TRANSIT);
////            refundOrderPackage.setCargoCompany(orderPackage.getCargoCompany());
////            refundOrderPackage.setCargoStatus(OrderPackage.CargoStatus.package_accepted);
////            refundOrderPackage.setProductPaymentOnDelivery(orderPackage.getProductPaymentOnDelivery());
////            refundOrderPackage.setCreateAt(Instant.now());
////            refundOrderPackage.setUpdateAt(Instant.now());
////            refundOrderPackage.setShipmentId(UUID.randomUUID().toString());
////
////            List<OrderPackage> refundOrderPackages = new ArrayList<>();
////            refundOrderPackages.add(refundOrderPackage);
////            order.getOrderStatus().setOrderRefundPackages(refundOrderPackages);
////
////            Order saveOrder = orderRepository.save(order);
////
////            boolean allProductsFullyRefunded = true;
////
////            for (OrderItem orderItem : saveOrder.getOrderItems()) {
////                boolean matched = false;
////                for (OrderItem refundOrderItem : saveOrder.getRefundOrderItems()) {
////                    if (Objects.equals(refundOrderItem.getProduct().getId(), orderItem.getProduct().getId())) {
////                        if (Objects.equals(refundOrderItem.getQuantity(), orderItem.getQuantity())) {
////                            matched = true;
////                            break;
////                        }
////                    }
////                }
////                if (!matched) {
////                    allProductsFullyRefunded = false;
////                    break;
////                }
////            }
////
////            if (allProductsFullyRefunded){
////                saveOrder.getOrderStatus().setStatus(OrderStatus.Status.REFUNDED);
////                saveOrder.getOrderStatus().setColor(OrderStatus.Color.BLUE);
////            }else{
////                saveOrder.getOrderStatus().setStatus(OrderStatus.Status.PARTIAL_REFUNDED);
////                saveOrder.getOrderStatus().setColor(OrderStatus.Color.TURQUISE);
////            }
////            orderRepository.save(saveOrder);
////            return "Kargo iade edilecek şekilde ayarlanmıştır!";
//        } else
        if (orderPackageUpdateDto.getOrderPackageStatusCode().equals(OrderPackage.OrderPackageStatusCode.DELIVERED)) {

            orderPackage.setCargoStatus(OrderPackage.CargoStatus.delivered);
            orderPackage.setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode.DELIVERED);
            orderPackage.setUpdateAt(Instant.now());
            orderPackage.setLocation("Teslim edildi");
            orderPackageRepository.save(orderPackage);
            return "Kargo Teslim edilmiştir!";
        } else if (orderPackageUpdateDto.getOrderPackageStatusCode().equals(OrderPackage.OrderPackageStatusCode.FAILURE)) {
            orderPackage.setCargoStatus(OrderPackage.CargoStatus.cancel);
            orderPackage.setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode.FAILURE);
            orderPackage.setUpdateAt(Instant.now());
            orderPackage.setLocation("İptal edilmiştir!");
            orderPackageRepository.save(orderPackage);
            return "Kargo iptal edilmiştir!";
        }else
            throw new BadRequestException("Bilinmeyen durum!");
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
                System.out.println(" tracking id: " + trackingStatus.getId());
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

        System.out.println("findbycargoId");
        OrderPackage orderPackage = orderPackageRepository.findByCargoId(webhookTrackUpdatedPayload.getData().getId()).orElseThrow(()-> new NotFoundException("Kargo Paketi Bulunamadı"));
        System.out.println("kargoId: "+orderPackage.getCargoId());
        System.out.println("gelen kargo ID : "+ webhookTrackUpdatedPayload.getData().getId());
        System.out.println("gelen kargo id 2 : "+ webhookTrackUpdatedPayload.getData().getTrackingStatus().getId());
        orderPackage.setCargoStatus(OrderPackage.CargoStatus.valueOf(webhookTrackUpdatedPayload.getData().getTrackingStatus().getTrackingSubStatusCode()));
        orderPackage.setOrderPackageStatusCode(OrderPackage.OrderPackageStatusCode.valueOf(webhookTrackUpdatedPayload.getData().getTrackingStatus().getTrackingStatusCode()));
        orderPackage.setUpdateAt(Instant.parse(webhookTrackUpdatedPayload.getData().getTrackingStatus().getUpdateAt()));
        orderPackage.setLocation(webhookTrackUpdatedPayload.getData().getTrackingStatus().getLocationName());
        orderPackage.setResponsiveLabelURL(webhookTrackUpdatedPayload.getData().getTrackingUrl());
        orderPackageRepository.save(orderPackage);
        return "Sipariş paketi güncellendi";
    }
}
