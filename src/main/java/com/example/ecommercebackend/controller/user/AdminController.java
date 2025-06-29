package com.example.ecommercebackend.controller.user;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.user.admin.AdminCreateDto;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminCreateDto adminCreateDto) {
        return new ResponseEntity<>(adminService.createAdmin(adminCreateDto), HttpStatus.CREATED);
    }



}
