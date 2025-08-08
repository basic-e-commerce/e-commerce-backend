package com.example.ecommercebackend.dto.merchant.merchant;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.entity.merchant.OpenCloseHour;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class MerchantResponseDto {
    private int id;
    private String name;

    private String addressLink;
    private String phoneNo;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;
    private String emailPassword;
    private String instagram;
    private String instagramLink;
    private String footerDescription;
    private List<OpenCloseHour> openCloseHours;
    private ImageDetailDto coverImage;
    private int addressId;
    private String title;
    private String firstName;
    private String lastName;
    private String username;
    private String countryName;
    private String countryIso;
    private String city;
    private String cityCode;
    private String district;
    private Integer districtId;
    private String postalCode;
    private String addressPrhoneNo;
    private String addressLine1;
    private String geliverId;

    public MerchantResponseDto(int id, String name, String addressLink, String phoneNo, String email, BigDecimal minOrderAmount, BigDecimal shippingFee, String emailPassword, String instagram, String instagramLink, String footerDescription, List<OpenCloseHour> openCloseHours, ImageDetailDto coverImage, int addressId, String title, String firstName, String lastName, String username, String countryName, String countryIso, String city, String cityCode, String district, Integer districtId, String postalCode, String addressPrhoneNo, String addressLine1, String geliverId) {
        this.id = id;
        this.name = name;
        this.addressLink = addressLink;
        this.phoneNo = phoneNo;
        this.email = email;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
        this.emailPassword = emailPassword;
        this.instagram = instagram;
        this.instagramLink = instagramLink;
        this.footerDescription = footerDescription;
        this.openCloseHours = openCloseHours;
        this.coverImage = coverImage;
        this.addressId = addressId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.countryName = countryName;
        this.countryIso = countryIso;
        this.city = city;
        this.cityCode = cityCode;
        this.district = district;
        this.districtId = districtId;
        this.postalCode = postalCode;
        this.addressPrhoneNo = addressPrhoneNo;
        this.addressLine1 = addressLine1;
        this.geliverId = geliverId;
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

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddressPrhoneNo() {
        return addressPrhoneNo;
    }

    public void setAddressPrhoneNo(String addressPrhoneNo) {
        this.addressPrhoneNo = addressPrhoneNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getGeliverId() {
        return geliverId;
    }

    public void setGeliverId(String geliverId) {
        this.geliverId = geliverId;
    }
}
