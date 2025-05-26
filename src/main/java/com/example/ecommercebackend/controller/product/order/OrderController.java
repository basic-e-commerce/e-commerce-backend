package com.example.ecommercebackend.controller.product.order;

import com.example.ecommercebackend.dto.product.order.OrderCreateDto;
import com.example.ecommercebackend.dto.product.order.OrderDetailDto;
import com.example.ecommercebackend.dto.product.order.OrderFilterRequest;
import com.example.ecommercebackend.dto.product.order.OrderResponseDto;
import com.example.ecommercebackend.dto.product.products.ProductFilterRequest;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.service.product.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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
    public ResponseEntity<List<OrderDetailDto>> filter(@RequestBody OrderFilterRequest orderFilterRequest,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.filterOrder(orderFilterRequest,page,size),HttpStatus.OK);
    }

    @GetMapping("/total-price")
    public ResponseEntity<BigDecimal> getTotalPrice(@RequestParam Instant startDate,
                                                    @RequestParam Instant endDate) {
        return new ResponseEntity<>(orderService.getTotalPrice(startDate,endDate), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDetailDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.findSuccessOrderByUser(),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDto>> getAllOrdersWithProducts() {
        return new ResponseEntity<>(orderService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/by-order-code")
    public ResponseEntity<OrderDetailDto> findByOrderCode(@RequestParam String orderCode) {
        return new ResponseEntity<>(orderService.findOrderDetailByOrderCode(orderCode),HttpStatus.OK);
    }




}
