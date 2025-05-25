package com.example.ecommercebackend.dto.user.address;

import com.example.ecommercebackend.entity.product.shipping.Country;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AddressCreateDto {
    private String title;
    private String firstName;
    private String lastName;
    private String countryName;
    private String city;
    private String addressLine1;
    private String postalCode;
    private String phoneNo;

    public AddressCreateDto(String title, String firstName, String lastName, String countryName, String city, String addressLine1 , String postalCode, String phoneNo) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryName = countryName;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
