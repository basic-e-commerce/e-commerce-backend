package com.example.ecommercebackend.entity.product.bill;

import com.example.ecommercebackend.entity.product.order.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_seq")
    @SequenceGenerator(name = "billing_seq", sequenceName = "billing_seq", allocationSize = 1)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;

    @Column(nullable = false,name = "total_amount")
    private BigDecimal totalAmount;

    @Column(nullable = false , name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private Instant createdAt = Instant.now();

    public Billing(Order order, BigDecimal totalAmount, BigDecimal taxAmount) {
        this.order = order;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
    }

    public Billing() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
