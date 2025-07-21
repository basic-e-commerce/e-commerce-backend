package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.order.OrderPackageRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderPackageService {
    private final OrderPackageRepository orderPackageRepository;

    public OrderPackageService(OrderPackageRepository orderPackageRepository) {
        this.orderPackageRepository = orderPackageRepository;
    }

    public OrderPackage createOrderPackage(OrderPackage orderPackage) {
        return orderPackageRepository.save(orderPackage);
    }

    public OrderPackage findById(Integer id) {
        return orderPackageRepository.findById(id).orElseThrow(()-> new NotFoundException("Order Package Bulunamadı"));
    }

    public void save(OrderPackage orderPackage) {
        orderPackageRepository.save(orderPackage);
    }
}
