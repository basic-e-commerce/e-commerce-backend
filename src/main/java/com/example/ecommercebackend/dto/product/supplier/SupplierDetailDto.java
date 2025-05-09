package com.example.ecommercebackend.dto.product.supplier;

public class SupplierDetailDto {
    private Integer id;
    private String name;
    private String company;
    private String phoneNumber;
    private String address;
    private String note;

    public SupplierDetailDto(Integer id, String name, String company, String phoneNumber, String address, String note) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
