package com.example.ecommercebackend.exception;

public enum ExceptionMessage {
    // Admin
    USER_NOT_FOUND("user not found"),
    ALREADY_EXISTS("already exists"),
    PASSWORD_NOT_MATCHES("password not matches"),
    INVALID_USERNAME("invalid username"),
    WRONG_CREDENTIALS("wrong credentials"), EXPIRED_TOKEN("expired token"), CATEGORY_NOT_FOUND("category not found"),;

    private String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
