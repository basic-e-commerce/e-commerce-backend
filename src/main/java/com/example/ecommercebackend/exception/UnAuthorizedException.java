package com.example.ecommercebackend.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String string) {
        super(string);
    }
}
