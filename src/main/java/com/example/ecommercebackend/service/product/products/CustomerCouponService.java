package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.products.CustomerCouponRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CustomerCouponService {
    private final CustomerCouponRepository customerCouponRepository;

    public CustomerCouponService(CustomerCouponRepository customerCouponRepository) {
        this.customerCouponRepository = customerCouponRepository;
    }

    public CustomerCoupon save(CustomerCoupon customerCoupon) {
        return customerCouponRepository.save(customerCoupon);
    }

    public CustomerCoupon findCouponByCodeAndActive(String code, Customer customer) {
        return customerCouponRepository.findOne(Specification.where(hasCouponCode(code).and(hasCustomer(customer)))).orElse(null);
    }

    public CustomerCoupon findCouponAndCustomer(Coupon coupon, Customer customer) {
        return customerCouponRepository.findOne(Specification.where(hasCoupon(coupon).and(hasCustomer(customer)))).orElse(null);
    }

    public Specification<CustomerCoupon> hasCustomer(Customer customer) {
        return (Root<CustomerCoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
            customer == null ? null : cb.equal(root.get("customer"), customer);
    }

    public Specification<CustomerCoupon> hasCoupon(Coupon coupon) {
        return (Root<CustomerCoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                coupon == null ? null : cb.equal(root.get("coupon"), coupon);
    }

    public Specification<CustomerCoupon> hasCouponCode(String code) {
        return (Root<CustomerCoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                code == null ? null : cb.equal(root.get("coupon").get("code"), code);
    }


}
