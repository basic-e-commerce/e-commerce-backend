package com.example.ecommercebackend.entity.user;

import com.example.ecommercebackend.config.EncryptedStringConverter;
import com.example.ecommercebackend.entity.product.shipping.Country;
import jakarta.persistence.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private int id;

    private String title;
    private String shortName;

    @ManyToOne
    @JoinColumn(name = "country_id",nullable = false)
    private Country country;
    private String firstName;
    private String lastName;
    private String username;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @Convert(converter = EncryptedStringConverter.class)
    private String addressLine1;
    private String postalCode;
    @Convert(converter = EncryptedStringConverter.class)
    private String phoneNo;

    private Boolean isRecipientAddress;
    private String geliverId;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Address(String title, Country country, String firstName, String lastName, String username, City city, District district, String addressLine1, String postalCode, String phoneNo, Boolean isRecipientAddress, Boolean isActive) {
        this.title = title;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.city = city;
        this.district = district;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
        this.isRecipientAddress = isRecipientAddress;
        this.isActive = isActive;
        String uuid = UUID.randomUUID().toString();
        this.shortName = firstName+"-"+lastName+"-"+uuid.substring(0,8);
    }

    public Address() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getGeliverId() {
        return geliverId;
    }

    public void setGeliverId(String geliverId) {
        this.geliverId = geliverId;
    }

    public Boolean getRecipientAddress() {
        return isRecipientAddress;
    }

    public void setRecipientAddress(Boolean recipientAddress) {
        isRecipientAddress = recipientAddress;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
