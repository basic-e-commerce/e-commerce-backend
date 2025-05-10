package com.example.ecommercebackend.dto.merchant.merchant;

import com.example.ecommercebackend.dto.file.ImageDetailDto;

import java.math.BigDecimal;

public class MerchantResponseDto {
    private int id;
    private String name;
    private String address;
    private ImageDetailDto coverImage;
    private String phoneNumber;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;

    public MerchantResponseDto(int id, String name, String address, ImageDetailDto coverImage, String phoneNumber, String email, BigDecimal minOrderAmount, BigDecimal shippingFee) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.coverImage = coverImage;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ImageDetailDto getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageDetailDto coverImage) {
        this.coverImage = coverImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }
}
