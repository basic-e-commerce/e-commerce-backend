package com.example.ecommercebackend.entity.product.card;


import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.user.Customer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    @SequenceGenerator(name = "card_seq", sequenceName = "card_seq", allocationSize = 1)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CardItem> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "customer_coupon_id", referencedColumnName = "id")
    private CustomerCoupon customerCoupon;

    public Card(Customer customer) {
        this.customer = customer;
    }
    public Card() {

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

    public List<CardItem> getItems() {
        return items;
    }

    public void setItems(List<CardItem> items) {
        this.items = items;
    }

    public CustomerCoupon getCustomerCoupon() {
        return customerCoupon;
    }

    public void setCustomerCoupon(CustomerCoupon customerCoupon) {
        this.customerCoupon = customerCoupon;
    }
}
