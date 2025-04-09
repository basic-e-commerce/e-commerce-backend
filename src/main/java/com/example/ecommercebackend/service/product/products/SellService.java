package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.repository.product.products.SellRepository;
import org.springframework.stereotype.Service;

@Service
public class SellService {
    private final SellRepository sellRepository;
    private final ProductService productService;

    public SellService(SellRepository sellRepository, ProductService productService) {
        this.sellRepository = sellRepository;
        this.productService = productService;
    }

    public Sell save(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        product.setQuantity(product.getQuantity()-orderItem.getQuantity());
        Product save = productService.save(product);

        Sell sell = new Sell(
                save,
                orderItem.getPrice(),
                orderItem.getQuantity()
        );
        return sellRepository.save(sell);
    }
}
