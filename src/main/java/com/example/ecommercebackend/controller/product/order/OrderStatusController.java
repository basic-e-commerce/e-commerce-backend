package com.example.ecommercebackend.controller.product.order;

import com.example.ecommercebackend.service.product.order.OrderStatusService;
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

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody String payload,
                                        @RequestHeader Map<String, String> headers) {
        System.out.println("Headers:");
        headers.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("Body:");
        System.out.println(payload);
        return ResponseEntity.ok().build();
    }

}
