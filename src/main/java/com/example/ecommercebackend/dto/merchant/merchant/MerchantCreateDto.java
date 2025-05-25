package com.example.ecommercebackend.dto.merchant.merchant;

import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class MerchantCreateDto {
    private String name;
    private String firstName;
    private String lastName;
    private String title;
    private String countryName;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String phoneNo;
    MultipartFile image;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;

    public MerchantCreateDto(String name, String firstName, String lastName, String title, String countryName, String city, String addressLine1, String addressLine2, String postalCode, String phoneNo, MultipartFile image, String email, BigDecimal minOrderAmount, BigDecimal shippingFee) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.countryName = countryName;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.image = image;
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

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public MultipartFile getImage() {
        return image;
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
