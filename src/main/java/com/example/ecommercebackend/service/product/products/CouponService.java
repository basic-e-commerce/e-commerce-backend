package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.builder.product.products.coupon.CouponBuilder;
import com.example.ecommercebackend.dto.product.products.coupon.CouponCreateDto;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.repository.product.products.CouponRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.user.CustomerService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CustomerService customerService;
    private final CouponBuilder couponBuilder;
    private final ProductService productService;

    public CouponService(CouponRepository couponRepository, CustomerService customerService, CouponBuilder couponBuilder, ProductService productService) {
        this.couponRepository = couponRepository;
        this.customerService = customerService;
        this.couponBuilder = couponBuilder;
        this.productService = productService;
    }

    public Coupon createCoupon(CouponCreateDto couponCreateDto){
        Coupon coupon = couponBuilder.couponCreateDtoToCoupon(couponCreateDto);
        Set<Customer> customers = new HashSet<>();
        Set<Product> products = new HashSet<>();
        for (Integer customerId : couponCreateDto.getCustomerIds()){
            Customer customer = customerService.findById(customerId);
            customers.add(customer);
        }
        coupon.setCustomers(customers);

        for (Integer productId : couponCreateDto.getProductIds()){
            Product product = productService.findProductById(productId);
            products.add(product);
        }
        coupon.setProducts(products);
        return couponRepository.save(coupon);
    }
}
