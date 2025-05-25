package com.example.ecommercebackend.dto.merchant.merchant;

import java.math.BigDecimal;

public class MerchantUpdateDto {
    private String name;
    private String firstName;
    private String lastName;
    private String title;
    private String countryName;
    private String city;
    private String addressLine1;
    private String postalCode;
    private String phoneNo;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;

    public MerchantUpdateDto(String name, String firstName, String lastName, String title, String countryName, String city, String addressLine1 , String postalCode, String phoneNo, String email, BigDecimal minOrderAmount, BigDecimal shippingFee) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.countryName = countryName;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.email = email;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
    }

    public String getName() {
        return name;
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

    public String getEmail() {
        return email;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
