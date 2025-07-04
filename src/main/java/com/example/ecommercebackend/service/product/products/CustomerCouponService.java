package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.coupon.CouponCustomerResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponsCustomerDto;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.products.CouponRepository;
import com.example.ecommercebackend.repository.product.products.CustomerCouponRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerCouponService {
    private final CustomerCouponRepository customerCouponRepository;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    public CustomerCouponService(CustomerCouponRepository customerCouponRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.customerCouponRepository = customerCouponRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public CustomerCoupon save(CustomerCoupon customerCoupon) {
        return customerCouponRepository.save(customerCoupon);
    }

    public List<CouponCustomerResponseDto> customerHasCoupon(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer) {
            Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
            return customerCouponRepository.findAll(Specification.where(hasCustomer(customer)),sort).stream().map(customerCoupon -> {
                return new CouponCustomerResponseDto(
                        customerCoupon.getCoupon().getCode(),
                        customerCoupon.getCoupon().getDescription(),
                        customerCoupon.getCoupon().getCouponStartDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
                        customerCoupon.getCoupon().getCouponEndDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
                        customerCoupon.getCoupon().getActive(),
                        customerCoupon.getUsed()
                );
            }).collect(Collectors.toList());
        }else
            throw new BadRequestException("Geçersiz Kullanıcı");

    }

    public List<CouponResponseDto> customerhasCoupons(@NotNullParam Integer customerId,Integer page,Integer size) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new NotFoundException("Müşteri bulunamadı"));
        Sort sort = Sort.by(Sort.Direction.DESC, "usedAt");
        return customerCouponRepository.findAll(Specification.where(hasCustomer(customer)),sort).stream().map(customerCoupon->{
            return new CouponResponseDto(
                    customerCoupon.getUsed(),
                    (customerCoupon.getUsed() != null && customerCoupon.getUsed())
                            ? customerCoupon.getUsedAt().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime()
                            : null
            );
        }).collect(Collectors.toList());
    }

    public List<CouponsCustomerDto> couponsHasCustomer(@NotNullParam Integer couponId,Integer page,Integer size) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()-> new NotFoundException("Kupon Bulunamadı"));
        Sort sort = Sort.by(Sort.Direction.DESC, "usedAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerCouponRepository.findAll(Specification.where(hasCoupon(coupon)),pageable).stream().map(customerCoupon -> {
            return new CouponsCustomerDto(
                    customerCoupon.getCustomer().getId(),
                    customerCoupon.getCustomer().getFirstName(),
                    customerCoupon.getCustomer().getLastName(),
                    customerCoupon.getUsed(),
                    (customerCoupon.getUsed() != null && customerCoupon.getUsed())
                            ? customerCoupon.getUsedAt().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime()
                            : null
            );
        }).collect(Collectors.toList());
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
