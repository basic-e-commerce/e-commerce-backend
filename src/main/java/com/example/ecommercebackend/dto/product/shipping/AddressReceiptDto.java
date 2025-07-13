package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.entity.user.District;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressReceiptDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("cityCode")
    private String cityCode;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("districtID")
    private Integer districtID;

    @JsonProperty("districtName")
    private String districtName;

    @JsonProperty("district")
    private DistrictDto district;

    @JsonProperty("streetID")
    private String streetID;

    @JsonProperty("streetName")
    private String streetName;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("source")
    private String source;

    @JsonProperty("isDefaultSenderAddress")
    private Boolean isDefaultSenderAddress;

    @JsonProperty("isDefaultReturnAddress")
    private Boolean isDefaultReturnAddress;

    @JsonProperty("isRecipientAddress")
    private Boolean isRecipientAddress;

    @JsonProperty("isInvoiceAddress")
    private Boolean isInvoiceAddress;

    @JsonProperty("isActive")
    private Boolean isActive;

    public AddressReceiptDto(String id, String createdAt, String updatedAt, String name, String shortName, String email, String phone, String address1, String address2, String cityCode, String cityName, String city, String state, String zip, Integer districtID, String districtName, DistrictDto district, String streetID, String streetName, String countryCode, String countryName, String source, Boolean isDefaultSenderAddress, Boolean isDefaultReturnAddress, Boolean isRecipientAddress, Boolean isInvoiceAddress, Boolean isActive) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.shortName = shortName;
        this.email = email;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.districtID = districtID;
        this.districtName = districtName;
        this.district = district;
        this.streetID = streetID;
        this.streetName = streetName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.source = source;
        this.isDefaultSenderAddress = isDefaultSenderAddress;
        this.isDefaultReturnAddress = isDefaultReturnAddress;
        this.isRecipientAddress = isRecipientAddress;
        this.isInvoiceAddress = isInvoiceAddress;
        this.isActive = isActive;
    }

    public AddressReceiptDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public DistrictDto getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDto district) {
        this.district = district;
    }

    public String getStreetID() {
        return streetID;
    }

    public void setStreetID(String streetID) {
        this.streetID = streetID;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getDefaultSenderAddress() {
        return isDefaultSenderAddress;
    }

    public void setDefaultSenderAddress(Boolean defaultSenderAddress) {
        isDefaultSenderAddress = defaultSenderAddress;
    }

    public Boolean getDefaultReturnAddress() {
        return isDefaultReturnAddress;
    }

    public void setDefaultReturnAddress(Boolean defaultReturnAddress) {
        isDefaultReturnAddress = defaultReturnAddress;
    }

    public Boolean getRecipientAddress() {
        return isRecipientAddress;
    }

    public void setRecipientAddress(Boolean recipientAddress) {
        isRecipientAddress = recipientAddress;
    }

    public Boolean getInvoiceAddress() {
        return isInvoiceAddress;
    }

    public void setInvoiceAddress(Boolean invoiceAddress) {
        isInvoiceAddress = invoiceAddress;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
