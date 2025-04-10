package com.example.ecommercebackend.entity.payment;

import com.example.ecommercebackend.entity.product.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PROCESS;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Payment(String name, String surname, String username, String phoneNo,String country, String city, String zipCode, String cardHolderName, String conversationId, String paymentUniqId, Order order) {
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

    public enum PaymentStatus {
        SUCCESS,PROCESS,FAILED,REFUNDED;
    }
}
