package com.example.ecommercebackend.service.product.statistic;

import com.example.ecommercebackend.dto.product.sell.ProductSellDto;
import com.example.ecommercebackend.dto.product.sell.SellDetailRequestDto;
import com.example.ecommercebackend.dto.product.statistic.StatisticDetailDto;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.service.product.order.OrderService;
import com.example.ecommercebackend.service.product.products.ProductService;
import com.example.ecommercebackend.service.product.products.SellService;
import com.example.ecommercebackend.service.user.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final ProductService productService;
    private final OrderService orderService;
    private final SellService sellService;
    private final CustomerService customerService;

    public StatisticService(ProductService productService, OrderService orderService, SellService sellService, CustomerService customerService) {
        this.productService = productService;
        this.orderService = orderService;
        this.sellService = sellService;
        this.customerService = customerService;
    }

    public StatisticDetailDto filter(){
        return null;
    }

    public SellDetailDto getSellDetail(SellDetailRequestDto sellDetailRequestDto){
        return null;
    }
}
