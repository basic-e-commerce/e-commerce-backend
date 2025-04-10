package com.example.ecommercebackend.dto.merchant.merchant;

import java.math.BigDecimal;

public class MerchantUpdateDto {
    private String name;
    private String title;
    private Integer countryId;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String phoneNo;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;

    public MerchantUpdateDto(String name, String title, Integer countryId, String city, String addressLine1, String addressLine2, String postalCode, String phoneNo, String email, BigDecimal minOrderAmount, BigDecimal shippingFee) {
        this.name = name;
        this.title = title;
        this.countryId = countryId;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
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

    public Integer getCountryId() {
        return countryId;
    }

    public String getCity() {
        return city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
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
}
