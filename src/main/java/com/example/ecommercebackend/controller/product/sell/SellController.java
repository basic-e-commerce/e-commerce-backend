package com.example.ecommercebackend.controller.product.sell;


import com.example.ecommercebackend.dto.product.sell.ProductDaySell;
import com.example.ecommercebackend.dto.product.sell.ProductSellDayFilterRequestDto;
import com.example.ecommercebackend.dto.product.sell.ProductSellDto;
import com.example.ecommercebackend.dto.product.sell.ProductSellFilterRequestDto;
import com.example.ecommercebackend.service.product.products.SellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sell")
public class SellController {
    private final SellService sellService;

    public SellController(SellService sellService) {
        this.sellService = sellService;
    }

    @PostMapping
    public ResponseEntity<List<ProductSellDto>> getSellProducts(@RequestBody ProductSellFilterRequestDto productSellFilterRequestDto,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(sellService.getSellProducts(productSellFilterRequestDto,page,size), HttpStatus.OK);
    }

    @PostMapping("/day-sell")
    public ResponseEntity<List<ProductDaySell>> getSellProductsDaySell(@RequestBody ProductSellDayFilterRequestDto productSellFilterRequestDto) {
        return new ResponseEntity<>(sellService.getSellProductsDaySell(productSellFilterRequestDto), HttpStatus.OK);
    }


}
