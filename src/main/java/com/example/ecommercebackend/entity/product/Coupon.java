package com.example.ecommercebackend.entity.product;

import com.example.ecommercebackend.entity.user.Admin;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

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

    @Column(nullable = false, length = 50)
    private String discountType; // Örn: "percentage" veya "fixed_amount"

    @Column(nullable = false)
    private int timesUsed = 0;

    private Integer maxUsage;
    private BigDecimal orderAmountLimit;

    private Instant couponStartDate;
    private Instant couponEndDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Admin createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Admin updatedBy;

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
