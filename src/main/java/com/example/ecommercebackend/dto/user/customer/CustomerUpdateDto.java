package com.example.ecommercebackend.dto.user.customer;

import com.example.ecommercebackend.anotation.NotNullField;

public class CustomerUpdateDto {
    @NotNullField
    private String name;
    @NotNullField
    private String lastName;
    private String phoneNumber;

    public CustomerUpdateDto(String name, String lastName, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
