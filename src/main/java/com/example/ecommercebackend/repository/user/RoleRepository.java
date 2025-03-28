package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleNameEqualsIgnoreCase(String roleName);
    Optional<Role> findByRoleName(String roleName);
}
