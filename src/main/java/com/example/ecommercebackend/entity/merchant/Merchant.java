package com.example.ecommercebackend.entity.merchant;

import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.user.Address;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_seq")
    @SequenceGenerator(name = "merchant_seq", sequenceName = "merchant_seq", allocationSize = 1)
    private int id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String addressLink;

    @OneToOne
    private CoverImage coverImage;
    private String phoneNo;
    private String phoneNoLink;
    private String email;
    private String emailLink;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;
    private String emailPassword;
    private String instagram;
    private String instagramLink;
    private String wpLink;
    private String footerDescription;
    @ElementCollection
    @CollectionTable(name = "merchant_open_close_hours", joinColumns = @JoinColumn(name = "merchant_id"))
    private List<OpenCloseHour> openCloseHours = new LinkedList<>();

    public Merchant(
            String name,
            Address address,
            String addressLink,
            CoverImage coverImage,
            String phoneNo,
            String phoneNoLink,
            String email,
            String emailLink,
            BigDecimal minOrderAmount,
            BigDecimal shippingFee,
            String emailPassword,
            String instagram,
            String instagramLink,
            String wpLink,
            String footerDescription,
            List<OpenCloseHour> openCloseHours) {
        this.name = name;
        this.address = address;
        this.coverImage = coverImage;
        this.phoneNo = phoneNo;
        this.phoneNoLink = phoneNoLink;
        this.email = email;
        this.emailLink = emailLink;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
        this.emailPassword = emailPassword;
        this.instagram = instagram;
        this.instagramLink = instagramLink;
        this.wpLink = wpLink;
        this.footerDescription = footerDescription;
        this.openCloseHours = openCloseHours;
        this.addressLink = addressLink;
    }
    public Merchant() {}

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
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

    public List<OpenCloseHour> getOpenCloseHours() {
        return openCloseHours;
    }

    public void setOpenCloseHours(List<OpenCloseHour> openCloseHours) {
        this.openCloseHours = openCloseHours;
    }

    public String getAddressLink() {
        return addressLink;
    }

    public void setAddressLink(String addressLink) {
        this.addressLink = addressLink;
    }

    public String getPhoneNoLink() {
        return phoneNoLink;
    }

    public void setPhoneNoLink(String phoneNoLink) {
        this.phoneNoLink = phoneNoLink;
    }

    public String getEmailLink() {
        return emailLink;
    }

    public void setEmailLink(String emailLink) {
        this.emailLink = emailLink;
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

    public String getWpLink() {
        return wpLink;
    }

    public void setWpLink(String wpLink) {
        this.wpLink = wpLink;
    }

    public String getFooterDescription() {
        return footerDescription;
    }

    public void setFooterDescription(String footerDescription) {
        this.footerDescription = footerDescription;
    }
}
