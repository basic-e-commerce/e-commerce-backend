package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.anotation.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressApiDto {
    private String name;
    private String email;
    @ValidPhoneNumber
    private String phone;
    private String address1;
    private String address2;
    private String countryCode;
    private String cityName;
    private String cityCode;
    private String districtName;
    private Integer districtID;
    private String zip;
    @JsonProperty("isRecipientAddress")
    private Boolean isRecipientAddress;
    private String shortName;

    public AddressApiDto(String name, String email, String phone, String address1, String address2, String countryCode, String cityName, String cityCode, String districtName, Integer districtID, String zip, Boolean isRecipientAddress, String shortName) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.countryCode = countryCode;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.districtName = districtName;
        this.districtID = districtID;
        this.zip = zip;
        this.isRecipientAddress = isRecipientAddress;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean getRecipientAddress() {
        return isRecipientAddress;
    }

    public void setRecipientAddress(Boolean recipientAddress) {
        isRecipientAddress = recipientAddress;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
