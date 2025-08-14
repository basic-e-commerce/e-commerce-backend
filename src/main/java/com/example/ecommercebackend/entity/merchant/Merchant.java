package com.example.ecommercebackend.entity.merchant;

import com.example.ecommercebackend.config.EncryptedStringConverter;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.user.Address;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> sendingAddresses;

    @OneToOne(cascade = CascadeType.ALL)
    private Address defaultSendingAddress;

    @OneToOne
    private CoverImage coverImage;
    @Convert(converter = EncryptedStringConverter.class)
    private String phoneNo;
    private String email;
    private BigDecimal minOrderAmount;
    private BigDecimal shippingFee;
    @Convert(converter = EncryptedStringConverter.class)
    private String emailPassword;
    private String instagram;
    private String instagramLink;
    @Column(length = 5000)
    private String footerDescription;
    @ElementCollection
    @CollectionTable(name = "merchant_open_close_hours", joinColumns = @JoinColumn(name = "merchant_id"))
    private List<OpenCloseHour> openCloseHours = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<CustomCargoContract> customCargoContracts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private CustomCargoContract defaultCustomCargoContract;

    @Column(nullable = false,columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean geliver;

    public Merchant(
            String name,
            Address address,
            String addressLink,
            List<Address> sendingAddresses,
            CoverImage coverImage,
            String phoneNo,
            String email,
            BigDecimal minOrderAmount,
            BigDecimal shippingFee,
            String emailPassword,
            String instagram,
            String instagramLink,
            String footerDescription,
            List<OpenCloseHour> openCloseHours) {
        this.name = name;
        this.address = address;
        this.sendingAddresses = sendingAddresses;
        this.coverImage = coverImage;
        this.phoneNo = phoneNo;
        this.email = email;
        this.minOrderAmount = minOrderAmount;
        this.shippingFee = shippingFee;
        this.emailPassword = emailPassword;
        this.instagram = instagram;
        this.instagramLink = instagramLink;
        this.footerDescription = footerDescription;
        this.openCloseHours = openCloseHours;
        this.addressLink = addressLink;
        this.defaultSendingAddress = sendingAddresses.get(0);
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

    public List<Address> getSendingAddresses() {
        return sendingAddresses;
    }

    public void setSendingAddresses(List<Address> sendingAddresses) {
        this.sendingAddresses = sendingAddresses;
    }

    public Address getDefaultSendingAddress() {
        return defaultSendingAddress;
    }

    public void setDefaultSendingAddress(Address defaultSendingAddress) {
        this.defaultSendingAddress = defaultSendingAddress;
    }

    public List<CustomCargoContract> getCustomCargoContracts() {
        return customCargoContracts;
    }

    public void setCustomCargoContracts(List<CustomCargoContract> customCargoContracts) {
        this.customCargoContracts = customCargoContracts;
    }

    public CustomCargoContract getDefaultCustomCargoContract() {
        return defaultCustomCargoContract;
    }

    public void setDefaultCustomCargoContract(CustomCargoContract defaultCustomCargoContract) {
        this.defaultCustomCargoContract = defaultCustomCargoContract;
    }

    public Boolean getGeliver() {
        return geliver;
    }

    public void setGeliver(Boolean geliver) {
        this.geliver = geliver;
    }
}
