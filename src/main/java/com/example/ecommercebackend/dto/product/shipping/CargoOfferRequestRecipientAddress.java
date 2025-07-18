package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoOfferRequestRecipientAddress {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("cityCode")
    private String cityCode;

    @JsonProperty("districtName")
    private String districtName;

    public CargoOfferRequestRecipientAddress(String name, String email, String phone, String address1, String countryCode, String cityCode, String districtName) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address1 = address1;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.districtName = districtName;
    }

    public CargoOfferRequestRecipientAddress() {
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
}
