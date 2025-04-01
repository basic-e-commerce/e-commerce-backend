package com.example.ecommercebackend.dto.user.customer;

public class CustomerCreateDto {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String rePassword;
    private final String phoneNumber;

    public CustomerCreateDto(String firstName, String lastName, String username, String password, String rePassword, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
