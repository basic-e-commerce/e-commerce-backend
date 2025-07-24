package com.example.ecommercebackend.dto.user.address;

public class AddressDetailDto {
    private int id;
    private String title;
    private String firstName;
    private String lastName;
    private String username;
    private String countryName;
    private String countryCode;
    private String city;
    private String cityCode;
    private String district;
    private Integer districtId;
    private String postalCode;
    private String phoneNo;
    private String addressLine1;
    private String geliverId;

    public AddressDetailDto(int id, String title, String firstName, String lastName, String username, String countryName, String countryCode, String city, String cityCode, String district, Integer districtId, String postalCode, String phoneNo, String addressLine1, String geliverId) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.city = city;
        this.cityCode = cityCode;
        this.district = district;
        this.districtId = districtId;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.addressLine1 = addressLine1;
        this.geliverId = geliverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getGeliverId() {
        return geliverId;
    }

    public void setGeliverId(String geliverId) {
        this.geliverId = geliverId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
