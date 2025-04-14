package com.example.ecommercebackend.dto.user.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class CustomerCreateDto {
    private final String firstName;
    private final String lastName;
    @Email(message = "E-posta doğru formatta olmalıdır")
    private final String username;
    private final String password;
    private final String rePassword;

    public CustomerCreateDto(String firstName, String lastName, String username, String password, String rePassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRePassword() {
        return rePassword;
    }

}
