package com.example.ecommercebackend.dto.merchant.merchant;


import com.example.ecommercebackend.entity.merchant.OpenCloseHour;
import java.math.BigDecimal;
import java.util.LinkedList;


public class MerchantUpdateDto {
    private String name;
    private String firstName;
    private String lastName;
    private String title;
    private String countryName;
    private String cityCode;
    private Integer districtId;
    private String addressLine1;
    private String postalCode;
    private String phoneNo;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;
    private String emailPassword;
    private String instagram;
    private String instagramLink;
    private String footerDescription;
    private LinkedList<OpenCloseHour> openCloseHours;

    public MerchantUpdateDto(String name, String firstName, String lastName, String title, String countryName, String cityCode, Integer districtId, String addressLine1, String postalCode, String phoneNo, String email, BigDecimal minOrderAmount, BigDecimal shippingFee, String emailPassword, String instagram, String instagramLink, String footerDescription, LinkedList<OpenCloseHour> openCloseHours) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.countryName = countryName;
        this.cityCode = cityCode;
        this.districtId = districtId;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.email = email;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
        this.emailPassword = emailPassword;
        this.instagram = instagram;
        this.instagramLink = instagramLink;
        this.footerDescription = footerDescription;
        this.openCloseHours = openCloseHours;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getCityCode() {
        return cityCode;
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

    public String getEmailPassword() {
        return emailPassword;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getInstagramLink() {
        return instagramLink;
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
}
