package com.example.ecommercebackend.exception;

public enum ExceptionMessage {
    // Admin
    USER_NOT_FOUND("user not found"),
    USER_ALREADY_EXISTS("admin already exists"),
    PASSWORD_NOT_MATCHES("password not matches"),
    INVALID_USERNAME("invalid username"),
    WRONG_CREDENTIALS("wrong credentials");

    private String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
