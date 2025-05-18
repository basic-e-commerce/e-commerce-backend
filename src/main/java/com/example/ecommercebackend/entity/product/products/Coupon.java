package com.example.ecommercebackend.entity.product.products;

import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupon_seq")
    @SequenceGenerator(name = "coupon_seq", sequenceName = "coupon_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    private BigDecimal discountValue;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType; // Örn: "percentage" veya "fixed_amount"

    @Column(nullable = false)
    private int timesUsed = 0;

    private Integer maxUsage;
    private BigDecimal orderAmountLimit;

    private Instant couponStartDate;
    private Instant couponEndDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_coupons",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Customer> customers = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Admin createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Admin updatedBy;



    @PrePersist
    private void generateCouponData() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Admin admin) {
            this.createdBy = admin;
            this.updatedBy = admin;
        }
        Instant now = Instant.now();
        this.updatedAt = now;
        this.createdAt = now;
    }

    @PreUpdate
    private void updateCouponData() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Admin admin) {
            this.createdBy = admin;
            this.updatedBy = admin;
        }
        this.updatedAt = Instant.now();
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }


    public enum DiscountType{
        PERCENTAGE,FIXEDAMOUNT
    }

    public Coupon(String code, BigDecimal discountValue, DiscountType discountType, Integer maxUsage, BigDecimal orderAmountLimit, Instant couponStartDate, Instant couponEndDate) {
        this.code = code;
        this.discountValue = discountValue;
        this.discountType = discountType;
        this.maxUsage = maxUsage;
        this.orderAmountLimit = orderAmountLimit;
        this.couponStartDate = couponStartDate;
        this.couponEndDate = couponEndDate;
    }

    public Coupon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public Integer getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(Integer maxUsage) {
        this.maxUsage = maxUsage;
    }

    public BigDecimal getOrderAmountLimit() {
        return orderAmountLimit;
    }

    public void setOrderAmountLimit(BigDecimal orderAmountLimit) {
        this.orderAmountLimit = orderAmountLimit;
    }

    public Instant getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(Instant couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public Instant getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(Instant couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Admin getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Admin createdBy) {
        this.createdBy = createdBy;
    }

    public Admin getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Admin updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}


