package com.example.ecommercebackend.entity.payment;

import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    private long id;
    private String name;
    private String surname;
    private String username;
    private String phoneNo;
    private String country;
    private String city;
    private String zipCode;
    private String cardHolderName;
    private String conversationId;
    private String paymentUniqId;
    private BigDecimal totalAmount;
    private int installment;
    private String paymentId;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Sell> sells;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PROCESS;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Payment(String name, String surname, String username, String phoneNo, String country, String city, String zipCode, String cardHolderName, String conversationId, String paymentUniqId, BigDecimal totalAmount, int installment, String paymentId, Order order) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNo = phoneNo;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.cardHolderName = cardHolderName;
        this.conversationId = conversationId;
        this.paymentUniqId = paymentUniqId;
        this.totalAmount = totalAmount;
        this.installment = installment;
        this.paymentId = paymentId;
        this.order = order;
    }
    public Payment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getPaymentUniqId() {
        return paymentUniqId;
    }

    public void setPaymentUniqId(String paymentUniqId) {
        this.paymentUniqId = paymentUniqId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public Set<Sell> getSells() {
        return sells;
    }

    public void setSells(Set<Sell> sells) {
        this.sells = sells;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public enum PaymentStatus {
        SUCCESS,PROCESS,FAILED,REFUNDED;
    }
}
