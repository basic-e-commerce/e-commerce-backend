package com.example.ecommercebackend.dto.user.address;

import com.example.ecommercebackend.entity.product.shipping.Country;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AddressCreateDto {
    private String title;
    private Integer countryId;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String phoneNo;

    public AddressCreateDto(String title, Integer countryId, String city, String addressLine1, String addressLine2, String postalCode, String phoneNo) {
        this.title = title;
        this.countryId = countryId;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
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
}
