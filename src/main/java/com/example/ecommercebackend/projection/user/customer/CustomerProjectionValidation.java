package com.example.ecommercebackend.projection.user.customer;

import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.projection.user.role.RoleProjection;

import java.util.Set;

public interface CustomerProjectionValidation {
    Integer getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getPassword();
    Boolean isAccountNonLocked();
    Boolean isEnabled();

    Set<RoleDTO> getRoles();

}
