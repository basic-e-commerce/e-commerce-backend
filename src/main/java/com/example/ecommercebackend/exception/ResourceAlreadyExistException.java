package com.example.ecommercebackend.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String string) {
        super(string);
    }
}
