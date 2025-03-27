package com.example.ecommercebackend.builder.user;

import com.example.ecommercebackend.dto.user.admin.AdminCreateDto;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Role;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminBuilder {

    public Admin AdminCreateDtoToAdmin(AdminCreateDto adminCreateDto,String hashPassword, Set<Role> roles) {
        return new Admin(
                adminCreateDto.getFirstName(),
                adminCreateDto.getLastName(),
                adminCreateDto.getPhoneNumber(),
                adminCreateDto.getUsername(),
                hashPassword,
                roles,
                true,
                true
        );
    }

}
