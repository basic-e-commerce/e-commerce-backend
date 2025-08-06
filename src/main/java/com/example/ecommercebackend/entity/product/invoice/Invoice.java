package com.example.ecommercebackend.entity.product.invoice;

import com.example.ecommercebackend.config.EncryptedStringConverter;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Table per class inheritance
public abstract class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_seq")
    @SequenceGenerator(name = "billing_seq", sequenceName = "billing_seq", allocationSize = 1)
    private int id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Payment payment;

    @Column(nullable = false,name = "total_amount")
    private BigDecimal totalAmount;

    @Column(nullable = false , name = "tax_amount")
    private BigDecimal taxAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    private String firstName;
    private String lastName;
    private String username;
    private String countryName;
    private String city;
    private String cityCode;
    private String district;
    private Integer districtId;
    @Convert(converter = EncryptedStringConverter.class)
    private String addressLine1;
    private String postalCode;
    @Convert(converter = EncryptedStringConverter.class)
    private String phoneNo;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private Instant createdAt = Instant.now();

    public Invoice(Payment payment, BigDecimal totalAmount, BigDecimal taxAmount, InvoiceType invoiceType, String firstName, String lastName, String username, String countryName, String city, String cityCode, String district, Integer districtId, String addressLine1, String postalCode, String phoneNo) {
        this.payment = payment;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.invoiceType = invoiceType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.countryName = countryName;
        this.city = city;
        this.cityCode = cityCode;
        this.district = district;
        this.districtId = districtId;
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.phoneNo = phoneNo;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public enum InvoiceType {
        INDIVIDUAL,
        CORPORATE
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public String getPhoneNumber() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
