package com.example.ecommercebackend.entity.merchant;

import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/*
id: Tedarikçi için benzersiz bir UUID kimlik sağlar. @GeneratedValue ile UUID otomatik olarak oluşturulur.

supplierName: Tedarikçi adı, her tedarikçi için belirlenmiş olan ismi tutar. Bu alan NOT NULL olarak işaretlenmiştir.

company: Tedarikçinin bağlı olduğu şirketin adı. Opsiyonel bir alandır, yani null olabilir.

phoneNumber: Tedarikçinin telefon numarası. Opsiyonel bir alandır.

addressLine1 ve addressLine2: Tedarikçinin adresi. addressLine1 alanı zorunlu olup, addressLine2 opsiyoneldir.

country: Tedarikçinin bulunduğu ülke. @ManyToOne ilişkisiyle Country tablosuna bağlanır. Ülke, NOT NULL olmalıdır.

city: Tedarikçinin bulunduğu şehir. Opsiyonel bir alandır.

note: Tedarikçiyle ilgili herhangi bir açıklama veya not. Opsiyonel bir alandır.

createdAt ve updatedAt: Bu alanlar sırasıyla tedarikçinin oluşturulma ve güncellenme tarihlerini tutar. createdAt alanı yalnızca bir kez oluşturulurken atanır ve daha sonra güncellenmez. updatedAt her güncellenme işleminde yenilenir.

createdBy ve updatedBy: Bu alanlar, tedarikçinin oluşturulmasından ve güncellenmesinden sorumlu olan StaffAccount nesnelerine işaret eder. @ManyToOne ilişkisi kullanılarak bu alanlar, staff_accounts tablosundaki id alanına bağlanır.
 */


@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
    @SequenceGenerator(name = "supplier_seq", sequenceName = "supplier_seq", allocationSize = 1)
    private int id;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @Column(name = "company")
    private String company;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @Column(name = "city")
    private String city;

    @Column(name = "note")
    private String note;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "product_suppliers",
            joinColumns = @JoinColumn(name = "suppliers_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Admin createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Supplier(String supplierName, String company, String phoneNumber, String addressLine1, String addressLine2, Country country, String city, String note, Admin createdBy, Admin updatedBy) {
        this.supplierName = supplierName;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.city = city;
        this.note = note;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Supplier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Admin getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Admin createdBy) {
        this.createdBy = createdBy;
    }

    public Admin getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Admin updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
