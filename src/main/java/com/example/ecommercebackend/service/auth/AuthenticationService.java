package com.example.ecommercebackend.service.auth;

import com.example.ecommercebackend.dto.user.authentication.AuthenticationRequestDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    public AuthenticationResponseDto loginCustomer(AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {
    }

    public AuthenticationResponseDto loginAdmin(AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {
    }
}
