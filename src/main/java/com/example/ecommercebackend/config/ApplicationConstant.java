package com.example.ecommercebackend.config;

public enum ApplicationConstant {
    JWT_HEADER("Authorization");
    private String message;

    ApplicationConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
