package com.example.ecommercebackend.entity.product.order;

import com.example.ecommercebackend.entity.user.Admin;
import jakarta.persistence.*;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_statuses")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_seq")
    @SequenceGenerator(name = "order_status_seq", sequenceName = "order_status_seq", allocationSize = 1)
    private int id;

    @Column(name = "status_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderPackage> orderPackages = new ArrayList<>();

    @Column(name = "receipt_address_id")
    private String receiptAddressId;

    @Column(name = "sender_address_id")
    private String senderAddressId;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "privacy", nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy = Privacy.PUBLIC;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Admin createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    public String getReceiptAddressId() {
        return receiptAddressId;
    }

    public void setReceiptAddressId(String receiptAddressId) {
        this.receiptAddressId = receiptAddressId;
    }

    public List<OrderPackage> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackage> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public String getSenderAddressId() {
        return senderAddressId;
    }

    public void setSenderAddressId(String senderAddressId) {
        this.senderAddressId = senderAddressId;
    }

    public enum Privacy {
        PUBLIC,
        PRIVATE
    }

    public enum Status {
        PENDING("Ürün Ödeme sürecindedir"),
        APPROVED("Ürün ödemesi yapıldı, Kargo için Hazırlanıyor"),
        REFUNDED("Iade sipariş"),
        CANCEL("İptal Edilen Sipariş");

        private String message;

        Status(String s) {
            this.message = s;
        }
        public String getValue() {
            return message;
        }
    }



    public enum Color {
        RED,
        GREEN,
        BLUE,
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public OrderStatus(Status status, Privacy privacy, Color color) {
        this.status = status;
        this.privacy = privacy;
        this.color = color;
    }

    public OrderStatus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
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
