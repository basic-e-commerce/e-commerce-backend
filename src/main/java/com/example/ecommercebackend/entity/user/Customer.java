package com.example.ecommercebackend.entity.user;

import com.example.ecommercebackend.entity.product.card.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends User {

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Card card = new Card(this);

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
}
