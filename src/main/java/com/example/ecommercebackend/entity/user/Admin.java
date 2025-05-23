package com.example.ecommercebackend.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "admins")
public class Admin extends User {
    public Admin(String firstName, String lastName, String phoneNumber, String username, String password, Set<Role> roles, boolean accountNonLocked, boolean enabled) {
        super(firstName, lastName, phoneNumber, username, password, roles, accountNonLocked, enabled);
    }

    public Admin() {
    }
}
