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

    private Instant couponStartDate;
    private Instant couponEndDate;

    private Boolean isPublic;
    private Boolean isActive;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_coupons",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CustomerCoupon> customerCoupons = new HashSet<>();

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


    public Set<CustomerCoupon> getCustomerCoupons() {
        return customerCoupons;
    }

    public void setCustomerCoupons(Set<CustomerCoupon> customerCoupons) {
        this.customerCoupons = customerCoupons;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    public enum DiscountType{
        PERCENTAGE,FIXEDAMOUNT
    }


    public Coupon(String code, String description, BigDecimal discountValue, DiscountType discountType, Integer totalUsageLimit, BigDecimal minOrderAmountLimit, Instant couponStartDate, Instant couponEndDate, Boolean isPublic, Boolean isActive) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.discountType = discountType;
        this.totalUsageLimit = totalUsageLimit;
        this.minOrderAmountLimit = minOrderAmountLimit;
        this.couponStartDate = couponStartDate;
        this.couponEndDate = couponEndDate;
        this.isPublic = isPublic;
        this.isActive = isActive;
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


