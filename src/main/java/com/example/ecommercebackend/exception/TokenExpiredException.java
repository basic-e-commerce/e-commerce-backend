package com.example.ecommercebackend.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String tryLogin) {
        super(tryLogin);
    }
}
