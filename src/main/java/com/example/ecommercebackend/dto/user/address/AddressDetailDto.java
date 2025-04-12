package com.example.ecommercebackend.dto.user.address;

public class AddressDetailDto {
    private int id;
    private String title;
    private String country;
    private String city;
    private String postalCode;
    private String phoneNo;
    private String addressLine1;

    public AddressDetailDto(int id, String title, String country, String city, String postalCode, String phoneNo, String addressLine1) {
        this.id = id;
        this.title = title;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.addressLine1 = addressLine1;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}
