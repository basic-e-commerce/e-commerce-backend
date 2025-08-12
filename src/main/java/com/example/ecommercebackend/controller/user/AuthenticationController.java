package com.example.ecommercebackend.controller.user;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationRequestDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationResponseDto;
import com.example.ecommercebackend.dto.user.customer.VerificationPasswordDto;
import com.example.ecommercebackend.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/c-login")
    public ResponseEntity<AuthenticationResponseDto> loginCustomer(@RequestBody(required = false) AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.loginCustomer(authenticationRequestDto,response), HttpStatus.OK);
    }
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/a-login")
    public ResponseEntity<AuthenticationResponseDto> loginAdmin(@RequestBody(required = false) AuthenticationRequestDto authenticationRequestDto,HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.loginAdmin(authenticationRequestDto,response), HttpStatus.OK);
    }
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refresh(@CookieValue(name = "refresh_token")String refreshToken){
        return new ResponseEntity<>(authenticationService.refresh(refreshToken),HttpStatus.OK);
    }
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/refresh/logout")
    public ResponseEntity<String> logout(@CookieValue(name = "refresh_token")String refreshToken,HttpServletResponse response){
        return new ResponseEntity<>(authenticationService.logout(refreshToken,response),HttpStatus.OK);
    }
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/is-auth")
    public ResponseEntity<Boolean> isAuth() {
        return new ResponseEntity<>(authenticationService.isAuth(),HttpStatus.OK);
    }
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @GetMapping("/verification/{code}")
    public ResponseEntity<String> verification(@PathVariable("code") String code,HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.verification(code,response),HttpStatus.OK);
    }
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username) {
        return new ResponseEntity<>(authenticationService.resetPassword(username),HttpStatus.OK);
    }
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    @PutMapping("/verification-password")
    public ResponseEntity<String> verificationPassword(@RequestBody(required = false) VerificationPasswordDto verificationPasswordDto) {
        return new ResponseEntity<>(authenticationService.verificationPassword(verificationPasswordDto),HttpStatus.OK);
    }

}
