package com.example.ecommercebackend.dto.product.invoice;

import com.example.ecommercebackend.entity.product.invoice.CorporateInvoice;

import java.math.BigDecimal;

public class InvoiceResponseDto {
    private int id;
    private String invoiceType;
    private String firstName;
    private String lastName;
    private String username;
    private String countryName;
    private String city;
    private String addressLine1;
    private String postalCode;
    private String phoneNo;
    private String createAt;
    private CorporateInvoiceResponseDto corporateInvoice;

    public InvoiceResponseDto(int id, String invoiceType, String firstName, String lastName, String username, String countryName, String city, String addressLine1, String postalCode, String phoneNo, String createAt) {
        this.id = id;
        this.invoiceType = invoiceType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.countryName = countryName;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public CorporateInvoiceResponseDto getCorporateInvoice() {
        return corporateInvoice;
    }

    public void setCorporateInvoice(CorporateInvoiceResponseDto corporateInvoice) {
        this.corporateInvoice = corporateInvoice;
    }
}
