package com.example.ecommercebackend.entity.product.shipping;

import com.example.ecommercebackend.entity.user.Admin;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Bu tablo, nakliye bölgesi kavramını yönetir. Bir e-ticaret sitesinde, farklı bölgeler için farklı nakliye koşulları (ücret, hız, bedava kargo vb.) belirlenebilir. Bu, nakliye ücretlerini ve servisleri bölgelere göre ayarlamaya olanak tanır.
 *
 * Mantık:
 * name ve display_name: Bölgenin adı ve görüntü ismi. Her bölgenin eşsiz bir adı ve gösterilen bir ismi vardır.
 *
 * active: Bölgelerin aktif olup olmadığını belirler. Eğer bölge aktif değilse, bu bölge için kargo yapılamaz.
 *
 * free_shipping: Bu özellik, belirli bir bölgeye bedava kargo yapılacağını belirtir.
 *
 * rate_type: Nakliye ücretinin nasıl hesaplanacağını belirtir. İki tip bulunur:
 *
 * 'price': Ücret ürünün fiyatına göre hesaplanır.
 *
 * 'weight': Ücret ürünün ağırlığına göre hesaplanır.
 *
 * created_at ve updated_at: Bölgenin yaratılma ve güncellenme tarihleri. Bu, verilerin ne zaman oluşturulduğunu ve sonrasında ne zaman değiştirildiğini takip etmek için kullanılır.
 */

@Entity
@Table(name = "shipping_zones")
public class ShippingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_zone_seq")
    @SequenceGenerator(name = "shipping_zone_seq", sequenceName = "shipping_zone_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String displayName;

    private Boolean active = false;
    private Boolean freeShipping = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 64)
    private RateType rateType;

    @ManyToMany
    @JoinTable(
            name = "shipping_country_zones",
            joinColumns = @JoinColumn(name = "shipping_zone_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Set<Country> countries = new HashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Admin createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public enum RateType {
        PRICE, WEIGHT
    }

    public ShippingZone(String name, String displayName, Boolean active, Boolean freeShipping, RateType rateType, Admin createdBy, Admin updatedBy) {
        this.name = name;
        this.displayName = displayName;
        this.active = active;
        this.freeShipping = freeShipping;
        this.rateType = rateType;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public ShippingZone() {
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
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
}
