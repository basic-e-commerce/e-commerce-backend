package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.builder.user.AdminBuilder;
import com.example.ecommercebackend.config.validation.RegexValidation;
import com.example.ecommercebackend.dto.user.admin.AdminCreateDto;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.user.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminBuilder adminBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RegexValidation regexValidation;
    private final RoleService roleService;

    public AdminService(AdminRepository adminRepository, AdminBuilder adminBuilder, PasswordEncoder passwordEncoder, RegexValidation regexValidation, RoleService roleService) {
        this.adminRepository = adminRepository;
        this.adminBuilder = adminBuilder;
        this.passwordEncoder = passwordEncoder;
        this.regexValidation = regexValidation;
        this.roleService = roleService;
    }

    public Admin createAdmin(AdminCreateDto adminCreateDto) {

        /*
        if(!regexValidation.isValidEmail(adminCreateDto.getUsername()))
            throw new BadRequestException(ExceptionMessage.INVALID_USERNAME.getMessage());

        if (regexValidation.isValidPassword(adminCreateDto.getPassword()))
            throw new BadRequestException(ExceptionMessage.INVALID_USERNAME.getMessage());
        */

        if (isAdminExistByUsername(adminCreateDto.getUsername()))
            throw new ResourceAlreadyExistException(ExceptionMessage.USER_ALREADY_EXISTS.getMessage());

        if (isAdminExistByPhoneNumber(adminCreateDto.getPhoneNumber()))
            throw new ResourceAlreadyExistException(ExceptionMessage.USER_ALREADY_EXISTS.getMessage());

        if (!adminCreateDto.getPassword().equals(adminCreateDto.getRePassword()))
            throw new BadRequestException(ExceptionMessage.PASSWORD_NOT_MATCHES.getMessage());

        String hashPassword = passwordEncoder.encode(adminCreateDto.getPassword());
        Set<Role> roles = new HashSet<>();
        Admin admin = adminBuilder.AdminCreateDtoToAdmin(adminCreateDto,hashPassword,roles);

        return adminRepository.save(admin);
    }

    private boolean isAdminExistByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    private boolean isAdminExistByPhoneNumber(String phoneNumber) {
        return adminRepository.existsByUsername(phoneNumber);
    }
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(()-> new NotFoundException(ExceptionMessage.USER_NOT_FOUND.getMessage()));
    }
}
