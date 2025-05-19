package com.example.ecommercebackend.dto.user.customer;

public class CustomerProfileDto {
    private String name;
    private String lastName;
    private String username;
    private String phoneNumber;

    public CustomerProfileDto(String name, String lastName, String username, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
