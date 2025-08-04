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
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType; // Örn: "percentage" yüzde veya "fixed_amount" sabit miktar

    private BigDecimal discountValue;

    @Column(nullable = false)
    private int timesUsed = 0;

    private Integer totalUsageLimit; // Genel kullanım limiti (opsiyonel)

    private BigDecimal minOrderAmountLimit;
    private BigDecimal maxOrderAmountLimit;

    private Instant couponStartDate;
    private Instant couponEndDate;
    private Boolean isActive;
    private Boolean isCustomerAssigned;
    private Boolean isProductAssigned;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_coupons",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "coupons_has_customer",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
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

    public Coupon(String code, String description, DiscountType discountType, BigDecimal discountValue, int timesUsed, Integer totalUsageLimit, BigDecimal minOrderAmountLimit, BigDecimal maxOrderAmountLimit, Instant couponStartDate, Instant couponEndDate, Boolean isActive, Boolean isCustomerAssigned, Boolean isProductAssigned) {
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.timesUsed = timesUsed;
        this.totalUsageLimit = totalUsageLimit;
        this.minOrderAmountLimit = minOrderAmountLimit;
        this.maxOrderAmountLimit = maxOrderAmountLimit;
        this.couponStartDate = couponStartDate;
        this.couponEndDate = couponEndDate;
        this.isActive = isActive;
        this.isCustomerAssigned = isCustomerAssigned;
        this.isProductAssigned = isProductAssigned;
    }

    public Coupon() {
    }

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Boolean getCustomerAssigned() {
        return isCustomerAssigned;
    }

    public void setCustomerAssigned(Boolean customerAssigned) {
        isCustomerAssigned = customerAssigned;
    }

    public Boolean getProductAssigned() {
        return isProductAssigned;
    }

    public void setProductAssigned(Boolean productAssigned) {
        isProductAssigned = productAssigned;
    }

    public BigDecimal getMaxOrderAmountLimit() {
        return maxOrderAmountLimit;
    }

    public void setMaxOrderAmountLimit(BigDecimal maxOrderAmountLimit) {
        this.maxOrderAmountLimit = maxOrderAmountLimit;
    }

    public enum DiscountType{
        PERCENTAGE,FIXEDAMOUNT
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public Integer getTotalUsageLimit() {
        return totalUsageLimit;
    }

    public void setTotalUsageLimit(Integer totalUsageLimit) {
        this.totalUsageLimit = totalUsageLimit;
    }

    public BigDecimal getMinOrderAmountLimit() {
        return minOrderAmountLimit;
    }

    public void setMinOrderAmountLimit(BigDecimal minOrderAmountLimit) {
        this.minOrderAmountLimit = minOrderAmountLimit;
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
}


