package com.example.ecommercebackend.controller.product.order;

import com.example.ecommercebackend.dto.product.order.OrderPackageResponseDto;
import com.example.ecommercebackend.dto.product.order.WebhookTrackUpdatedPayload;
import com.example.ecommercebackend.dto.product.shipping.OrderPackageUpdateDto;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.service.product.order.OrderPackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-package")
public class OrderPackageController {
    private final OrderPackageService orderPackageService;

    public OrderPackageController(OrderPackageService orderPackageService) {
        this.orderPackageService = orderPackageService;
    }

    @PostMapping("/track-update")
    public ResponseEntity<String> trackUpdate(@RequestBody WebhookTrackUpdatedPayload webhookTrackUpdatedPayload) {
        return new ResponseEntity<>(orderPackageService.trackUpdate(webhookTrackUpdatedPayload), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<OrderPackageResponseDto> getDetails(@RequestParam Integer id) {
        return new ResponseEntity<>(orderPackageService.getDetails(id), HttpStatus.OK);
    }

    @PutMapping("/manuel-update")
    public ResponseEntity<String> orderPackageUpdate(@RequestParam(required = false) Integer orderPackageId, @RequestBody(required = false) OrderPackageUpdateDto orderPackageUpdateDto){
        return new ResponseEntity<>(orderPackageService.orderPackageUpdate(orderPackageId, orderPackageUpdateDto), HttpStatus.OK);
    }
}
