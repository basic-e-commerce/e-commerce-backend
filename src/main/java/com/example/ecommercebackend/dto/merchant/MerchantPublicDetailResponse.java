package com.example.ecommercebackend.dto.merchant;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.entity.merchant.OpenCloseHour;

import java.math.BigDecimal;
import java.util.List;

public class MerchantPublicDetailResponse {
    private int id;
    private String name;
    private String addressLink;
    private String phoneNo;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;
    private String instagram;
    private String instagramLink;
    private String footerDescription;
    private List<OpenCloseHour> openCloseHours;
    private ImageDetailDto coverImage;
    private AddressDetailDto address;


    public MerchantPublicDetailResponse(int id, String name, String addressLink, String phoneNo, String email, BigDecimal minOrderAmount, BigDecimal shippingFee, String instagram, String instagramLink, String footerDescription, List<OpenCloseHour> openCloseHours, ImageDetailDto coverImage, AddressDetailDto address) {
        this.id = id;
        this.name = name;
        this.addressLink = addressLink;
        this.phoneNo = phoneNo;
        this.email = email;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
        this.instagram = instagram;
        this.instagramLink = instagramLink;
        this.footerDescription = footerDescription;
        this.openCloseHours = openCloseHours;
        this.coverImage = coverImage;
        this.address = address;
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

    public String getAddressLink() {
        return addressLink;
    }

    public void setAddressLink(String addressLink) {
        this.addressLink = addressLink;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getFooterDescription() {
        return footerDescription;
    }

    public void setFooterDescription(String footerDescription) {
        this.footerDescription = footerDescription;
    }

    public List<OpenCloseHour> getOpenCloseHours() {
        return openCloseHours;
    }

    public void setOpenCloseHours(List<OpenCloseHour> openCloseHours) {
        this.openCloseHours = openCloseHours;
    }

    public ImageDetailDto getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageDetailDto coverImage) {
        this.coverImage = coverImage;
    }

    public AddressDetailDto getAddress() {
        return address;
    }

    public void setAddress(AddressDetailDto address) {
        this.address = address;
    }
}
