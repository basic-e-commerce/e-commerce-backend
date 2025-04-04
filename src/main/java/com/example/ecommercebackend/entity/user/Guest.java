package com.example.ecommercebackend.entity.user;

import com.example.ecommercebackend.entity.product.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "guest")
public class Guest extends User {
    @JsonIgnore
    @OneToMany
    private Set<Order> orders = new HashSet<>();
    public Guest(String firstName, String lastName, String phoneNumber, String username, String password, Set<Role> roles, boolean accountNonLocked, boolean enabled) {
        super(firstName, lastName, phoneNumber, username, password, roles, accountNonLocked, enabled);
    }
    public Guest() {}

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
