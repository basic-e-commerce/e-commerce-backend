package com.example.ecommercebackend.dto.merchant.merchant;

import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.merchant.OpenCloseHour;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class MerchantCreateDto {
    private String name;
    private String firstName;
    private String lastName;
    private String title;
    private String countryName;
    private String city;
    private String addressLine1;
    private String postalCode;
    private String addressLink;
    private String phoneNo;
    private String phoneNoLink;
    MultipartFile image;
    private String email;
    private String emailLink;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;
    private String emailPassword;
    private String instagram;
    private String instagramLink;
    private String wpLink;
    private String footerDescription;
    private LinkedList<OpenCloseHour> openCloseHours;

    public MerchantCreateDto(String name, String firstName, String lastName, String title, String countryName, String city, String addressLine1, String postalCode, String addressLink, String phoneNo, String phoneNoLink, MultipartFile image, String email, String emailLink, BigDecimal minOrderAmount, BigDecimal shippingFee, String emailPassword, String instagram, String instagramLink, String wpLink, String footerDescription, LinkedList<OpenCloseHour> openCloseHours) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.countryName = countryName;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.addressLink = addressLink;
        this.phoneNo = phoneNo;
        this.phoneNoLink = phoneNoLink;
        this.image = image;
        this.email = email;
        this.emailLink = emailLink;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
        this.emailPassword = emailPassword;
        this.instagram = instagram;
        this.instagramLink = instagramLink;
        this.wpLink = wpLink;
        this.footerDescription = footerDescription;
        this.openCloseHours = openCloseHours;
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

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getAddressLink() {
        return addressLink;
    }

    public String getPhoneNoLink() {
        return phoneNoLink;
    }

    public String getEmailLink() {
        return emailLink;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public String getWpLink() {
        return wpLink;
    }

    public String getFooterDescription() {
        return footerDescription;
    }

    public LinkedList<OpenCloseHour> getOpenCloseHours() {
        return openCloseHours;
    }
}
