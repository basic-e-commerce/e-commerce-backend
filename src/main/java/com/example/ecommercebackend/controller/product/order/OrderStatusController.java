package com.example.ecommercebackend.controller.product.order;

import com.example.ecommercebackend.dto.product.order.WebhookTrackUpdatedPayload;
import com.example.ecommercebackend.service.product.order.OrderStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order-status")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

}
