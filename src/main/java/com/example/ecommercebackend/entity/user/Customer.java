package com.example.ecommercebackend.entity.user;

import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends User {

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Card card;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany
    private Set<Coupon> coupons = new HashSet<>();


    public Customer(int id, String firstName, String lastName, String username, String password, Set<Role> roles, boolean enabled, boolean accountNonLocked) {
        super(id, firstName, lastName, username, password, roles, enabled, accountNonLocked);
    }

    public Customer(String firstName, String lastName, String phoneNumber, String username, String password, Set<Role> roles, boolean accountNonLocked, boolean enabled) {
        super(firstName, lastName, phoneNumber, username, password, roles, accountNonLocked, enabled);
    }

    public Customer() {
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }
}
