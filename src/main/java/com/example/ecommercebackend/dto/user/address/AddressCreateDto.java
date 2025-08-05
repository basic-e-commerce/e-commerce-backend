package com.example.ecommercebackend.dto.user.address;

import com.example.ecommercebackend.anotation.ValidPhoneNumber;
import com.example.ecommercebackend.entity.product.shipping.Country;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AddressCreateDto {
    private String title;
    private String firstName;
    private String lastName;
    private String username;
    private String countryName;
    private String cityCode;
    private Integer districtId;
    private String addressLine1;
    private String postalCode;
    @ValidPhoneNumber
    private String phoneNo;

    public AddressCreateDto(String title, String firstName, String lastName, String username, String countryName, String cityCode, Integer districtId, String addressLine1, String postalCode, String phoneNo) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.countryName = countryName;
        this.cityCode = cityCode;
        this.districtId = districtId;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
