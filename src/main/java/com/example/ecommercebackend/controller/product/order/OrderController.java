package com.example.ecommercebackend.controller.product.order;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.order.*;
import com.example.ecommercebackend.dto.product.products.ProductFilterRequest;
import com.example.ecommercebackend.dto.product.products.productTemplate.CargoOfferDesiRequestAdminDto;
import com.example.ecommercebackend.dto.product.shipping.OfferApproveDto;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.service.product.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(orderService.createOrder(orderCreateDto), HttpStatus.CREATED);
    }
*/

    @PostMapping("/filter")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<OrderDetailDto>> filter(@RequestBody OrderFilterRequest orderFilterRequest,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.filterOrder(orderFilterRequest,page,size),HttpStatus.OK);
    }

    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/total-price")
    public ResponseEntity<BigDecimal> getTotalPrice(@RequestParam Instant startDate,
                                                    @RequestParam Instant endDate) {
        return new ResponseEntity<>(orderService.getTotalPrice(startDate,endDate), HttpStatus.OK);
    }

    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/user")
    public ResponseEntity<List<OrderDetailDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.findSuccessOrderByUser(),HttpStatus.OK);
    }

    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping
    public ResponseEntity<List<OrderDetailDto>> getAllOrdersWithProducts() {
        return new ResponseEntity<>(orderService.getAll(),HttpStatus.OK);
    }

    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/by-order-code")
    public ResponseEntity<OrderDetailDto> findByOrderCode(@RequestParam(required = false) String orderCode) {
        return new ResponseEntity<>(orderService.findOrderDetailByOrderCode(orderCode),HttpStatus.OK);
    }


    @PostMapping("/cargo-offer")
    public ResponseEntity<CargoOfferResponsesDto> cargoOffer(@RequestParam(required = false) String orderCode,
                                                             @RequestBody(required = false)List<CargoOfferDesiRequestAdminDto> cargoOfferDesiRequestAdminDtos){
        return new ResponseEntity<>(orderService.cargoOffer(orderCode,cargoOfferDesiRequestAdminDtos),HttpStatus.OK);
    }

    @PostMapping("/offer-approve")
    public ResponseEntity<OfferApproveDto> offerApprove(@RequestParam(required = false) Integer orderPackageId,@RequestParam(required = false) String offerId){
        return new ResponseEntity<>(orderService.offerApprove(orderPackageId,offerId),HttpStatus.OK);
    }



}
