package com.example.ecommercebackend.controller.product.sell;


import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.card.ProductCardItemDto;
import com.example.ecommercebackend.dto.product.products.ProductAdminDetailDto;
import com.example.ecommercebackend.dto.product.sell.*;
import com.example.ecommercebackend.dto.user.TimeDto;
import com.example.ecommercebackend.service.product.products.SellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/sell")
public class SellController {
    private final SellService sellService;

    public SellController(SellService sellService) {
        this.sellService = sellService;
    }

    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping
    public ResponseEntity<List<ProductSellDto>> getSellProducts(@RequestBody ProductSellFilterRequestDto productSellFilterRequestDto,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(sellService.getSellProducts(productSellFilterRequestDto,page,size), HttpStatus.OK);
    }

    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/day-sell")
    public ResponseEntity<ProductDaySellAdmin> getSellProductsDaySell(@RequestBody ProductSellDayFilterRequestDto productSellFilterRequestDto) {
        return new ResponseEntity<>(sellService.getOrderProductsDaySell(productSellFilterRequestDto), HttpStatus.OK);
    }

    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/customer-register")
    public ResponseEntity<Integer> newCustomerRegister(@RequestBody(required = false) TimeDto timeDto){
        return new ResponseEntity<>(sellService.newCustomerRegister(timeDto), HttpStatus.OK);

    }

    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/sell-product")
    public ResponseEntity<List<ProductSmallSellDto>> getSellProduct(@RequestBody(required = false) TimeDto timeDto) {
        return new ResponseEntity<>(sellService.getSellProduct(timeDto), HttpStatus.OK);
    }


    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/card-contain-product")
    public ResponseEntity<List<ProductCardItemDto>> cardItemContainsProduct(@RequestBody(required = false) TimeDto timeDto) {
        return new ResponseEntity<>(sellService.cardItemContainsProduct(timeDto), HttpStatus.OK);
    }

    @RateLimit(limit = 7, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/low-product")
    public ResponseEntity<List<ProductAdminDetailDto>> getLowProduct(){
        return new ResponseEntity<>(sellService.lowStock(), HttpStatus.OK);
    }



}
