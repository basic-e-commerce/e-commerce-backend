package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.user.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName) {
        if (roleRepository.existsByRoleNameEqualsIgnoreCase(roleName))
            throw new ResourceAlreadyExistException("Role: "+ExceptionMessage.ALREADY_EXISTS.getMessage());

        Role role = new Role(roleName.toUpperCase(Locale.ROOT));
        return roleRepository.save(role);
    }
}
