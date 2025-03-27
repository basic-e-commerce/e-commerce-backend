package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
