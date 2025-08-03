package com.example.ecommercebackend.entity.product.products;

import com.example.ecommercebackend.entity.user.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "customer_coupons",
        uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "coupon_id"})
)
public class CustomerCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_coupon_seq")
    @SequenceGenerator(name = "customer_coupon_seq", sequenceName = "customer_coupon_seq", allocationSize = 1)
    private int id;
    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Coupon coupon;

    private Boolean isUsed = false;
    private BigDecimal discountPrice;
    private Integer usedQuantity = 0;
    private Instant createAt;
    private Instant updateAt;
    private Instant usedAt;

    public CustomerCoupon(Customer customer, Coupon coupon, BigDecimal discountPrice, Instant createAt, Instant updateAt) {
        this.customer = customer;
        this.coupon = coupon;
        this.discountPrice = discountPrice;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public CustomerCoupon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Instant usedAt) {
        this.usedAt = usedAt;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }
}
