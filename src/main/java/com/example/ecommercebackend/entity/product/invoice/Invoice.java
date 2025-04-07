package com.example.ecommercebackend.entity.product.invoice;

import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Table per class inheritance
public abstract class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_seq")
    @SequenceGenerator(name = "billing_seq", sequenceName = "billing_seq", allocationSize = 1)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    private Payment payment;

    @Column(nullable = false,name = "total_amount")
    private BigDecimal totalAmount;

    @Column(nullable = false , name = "tax_amount")
    private BigDecimal taxAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private Instant createdAt = Instant.now();

    public Invoice(Payment payment, BigDecimal totalAmount, BigDecimal taxAmount, InvoiceType invoiceType) {
        this.payment = payment;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.invoiceType = invoiceType;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public enum InvoiceType {
        INDIVIDUAL,
        CORPORATE
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
