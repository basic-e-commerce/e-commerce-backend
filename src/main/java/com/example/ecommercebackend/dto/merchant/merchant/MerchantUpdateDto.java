package com.example.ecommercebackend.dto.merchant.merchant;

import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.entity.merchant.OpenCloseHour;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class MerchantUpdateDto {
    private String name;
    private String firstName;
    private String lastName;
    private String title;
    private String countryName;
    private String city;
    private Integer districtId;
    private String addressLine1;
    private String postalCode;
    private String phoneNo;
    private String phoneNoLink;
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
    private List<AddressApiDto> sendAddresses;

    public MerchantUpdateDto(String name, String firstName, String lastName, String title, String countryName, String city, Integer districtId, String addressLine1, String postalCode, String phoneNo, String phoneNoLink, String email, String emailLink, BigDecimal minOrderAmount, BigDecimal shippingFee, String emailPassword, String instagram, String instagramLink, String wpLink, String footerDescription, LinkedList<OpenCloseHour> openCloseHours, List<AddressApiDto> sendAddresses) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.countryName = countryName;
        this.city = city;
        this.districtId = districtId;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.phoneNoLink = phoneNoLink;
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
        this.sendAddresses = sendAddresses;
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

    public String getPhoneNoLink() {
        return phoneNoLink;
    }

    public String getEmailLink() {
        return emailLink;
    }

    public String getEmailPassword() {
        return emailPassword;
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

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public List<AddressApiDto> getSendAddresses() {
        return sendAddresses;
    }

    public void setSendAddresses(List<AddressApiDto> sendAddresses) {
        this.sendAddresses = sendAddresses;
    }
}
