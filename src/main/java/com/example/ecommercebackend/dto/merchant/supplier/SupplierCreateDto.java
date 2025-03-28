package com.example.ecommercebackend.dto.merchant.supplier;

public class SupplierCreateDto {
    private String supplierName;
    private String companyName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private int countryId;
    private String city;
    private String note;

    public SupplierCreateDto(String supplierName, String companyName, String phoneNumber, String addressLine1, String addressLine2, int countryId, String city, String note) {
        this.supplierName = supplierName;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.countryId = countryId;
        this.city = city;
        this.note = note;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCity() {
        return city;
    }

    public String getNote() {
        return note;
    }
}
