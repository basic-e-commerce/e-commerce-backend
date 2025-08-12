package com.example.ecommercebackend.dto.user.customer;

import org.springframework.web.bind.annotation.RequestParam;

public class VerificationPasswordDto {
    private String code;
    private String password;
    private String rePassword;

    public VerificationPasswordDto(String code, String password, String rePassword) {
        this.code = code;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
