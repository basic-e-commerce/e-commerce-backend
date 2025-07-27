package com.example.ecommercebackend.entity.product.order;

import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_coupon_id", referencedColumnName = "id")
    private CustomerCoupon customerCoupon;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "geliver_id")
    private String geliverId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_iso")
    private String countryIso;

    @Column(name = "city")
    private String city;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "district")
    private String district;

    @Column(name = "districtID")
    private Integer districtID;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<OrderItem> refundOrderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    private OrderStatus orderStatus;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "customer_price")
    private BigDecimal customerPrice;

    @Column(name = "refund_price", columnDefinition = "NUMERIC DEFAULT 0", nullable = false)
    private BigDecimal refundPrice = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Payment payments;

    @Column(name = "order_approved_at")
    private Instant orderApprovedAt;   // siparişin onaylanış tarihi

    @Column(name = "order_delivered_carrier_date")
    private Instant orderDeliveredCarrierDate;    // kargoya teslim

    @Column(name = "order_delivered_customer_date")
    private Instant orderDeliveredCustomerDate;   // müşteriye teslim

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Invoice invoice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        this.orderCode = UUID.randomUUID().toString();
    }

    public Order(User user, CustomerCoupon customerCoupon, String geliverId, String firstName, String lastName, String username, String countryName, String countryIso, String city,String cityCode, String district,Integer districtID, String addressLine1, String postalCode, String phoneNumber, Set<OrderItem> orderItems, OrderStatus orderStatus, BigDecimal totalPrice, BigDecimal price, Invoice invoice) {
        this.user = user;
        this.customerCoupon = customerCoupon;
        this.geliverId = geliverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.countryName = countryName;
        this.countryIso = countryIso;
        this.city = city;
        this.cityCode = cityCode;
        this.district = district;
        this.districtID = districtID;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.price = price;
        this.invoice = invoice;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerCoupon getCustomerCoupon() {
        return customerCoupon;
    }

    public void setCustomerCoupon(CustomerCoupon customerCoupon) {
        this.customerCoupon = customerCoupon;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getCountry() {
        return countryName;
    }

    public void setCountry(String countryName) {
        this.countryName = countryName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Payment getPayments() {
        return payments;
    }

    public void setPayments(Payment payments) {
        this.payments = payments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(BigDecimal customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getGeliverId() {
        return geliverId;
    }

    public void setGeliverId(String geliverId) {
        this.geliverId = geliverId;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public BigDecimal getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Set<OrderItem> getRefundOrderItems() {
        return refundOrderItems;
    }

    public void setRefundOrderItems(Set<OrderItem> refundOrderItems) {
        this.refundOrderItems = refundOrderItems;
    }
}
