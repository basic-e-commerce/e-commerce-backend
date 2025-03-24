package com.example.ecommercebackend.entity.product.order;

import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    private OrderStatus orderStatus;

    @Column(name = "order_approved_at")
    private Instant orderApprovedAt;   // siparişin onaylanış tarihi

    @Column(name = "order_delivered_carrier_date")
    private Instant orderDeliveredCarrierDate;    // kargoya teslim

    @Column(name = "order_delivered_customer_date")
    private Instant orderDeliveredCustomerDate;   // müşteriye teslim

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;


    public Order(Coupon coupon, Customer customer, OrderStatus orderStatus, Admin updatedBy) {
        this.coupon = coupon;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.updatedBy = updatedBy;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getOrderApprovedAt() {
        return orderApprovedAt;
    }

    public void setOrderApprovedAt(Instant orderApprovedAt) {
        this.orderApprovedAt = orderApprovedAt;
    }

    public Instant getOrderDeliveredCarrierDate() {
        return orderDeliveredCarrierDate;
    }

    public void setOrderDeliveredCarrierDate(Instant orderDeliveredCarrierDate) {
        this.orderDeliveredCarrierDate = orderDeliveredCarrierDate;
    }

    public Instant getOrderDeliveredCustomerDate() {
        return orderDeliveredCustomerDate;
    }

    public void setOrderDeliveredCustomerDate(Instant orderDeliveredCustomerDate) {
        this.orderDeliveredCustomerDate = orderDeliveredCustomerDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Admin getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Admin updatedBy) {
        this.updatedBy = updatedBy;
    }
}
