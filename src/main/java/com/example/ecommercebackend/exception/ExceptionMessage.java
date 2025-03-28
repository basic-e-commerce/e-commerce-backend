package com.example.ecommercebackend.exception;

public enum ExceptionMessage {
    // Admin
    NOT_FOUND("not found"),
    ALREADY_EXISTS("already exists"),
    PASSWORD_NOT_MATCHES("password not matches"),
    INVALID_USERNAME("invalid username"),
    WRONG_CREDENTIALS("wrong credentials"),
    EXPIRED_TOKEN("expired token"),
    NOT_NEGATIVE("not negative");

    private String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
