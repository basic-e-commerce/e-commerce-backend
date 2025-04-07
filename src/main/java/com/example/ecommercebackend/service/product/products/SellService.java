package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.repository.product.products.SellRepository;
import org.springframework.stereotype.Service;

@Service
public class SellService {
    private final SellRepository sellRepository;

    public SellService(SellRepository sellRepository) {
        this.sellRepository = sellRepository;
    }

    public Sell save(OrderItem orderItem) {
        Sell sell = new Sell(
                orderItem.getProduct(),
                orderItem.getPrice(),
                orderItem.getQuantity()
        );
        return sellRepository.save(sell);
    }
}
