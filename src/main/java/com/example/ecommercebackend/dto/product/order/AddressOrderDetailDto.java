package com.example.ecommercebackend.dto.product.order;

public class AddressOrderDetailDto {
    private String firstName;
    private String lastName;
    private String username;
    private String country;
    private String city;
    private String postalCode;
    private String phoneNo;
    private String addressLine1;

    public AddressOrderDetailDto(String firstName, String lastName, String username, String country, String city, String postalCode, String phoneNo, String addressLine1) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.addressLine1 = addressLine1;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
}
