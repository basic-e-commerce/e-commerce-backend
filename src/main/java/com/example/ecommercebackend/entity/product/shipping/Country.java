package com.example.ecommercebackend.entity.product.shipping;
/**
 * iso: Ülkenin 2 harfli uluslararası ISO kodu. (Örn: TR, US)
 *
 * name: Ülkenin tam adı. (Örn: Türkiye, United States)
 *
 * upper_name: Ülke adının büyük harflerle yazılmış hali. (Örn: TURKIYE, UNITED STATES)
 *
 * iso3: Ülkenin 3 harfli ISO kodu. (Örn: TUR, USA)
 *
 * num_code: Ülkenin numara kodu. (Örn: 792 - Türkiye, 840 - USA)
 *
 * phone_code: Ülkenin telefon kodu. (Örn: +90 - Türkiye, +1 - USA)
 */

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_seq")
    @SequenceGenerator(name = "countries_seq", sequenceName = "countries_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "iso", nullable = false, length = 2)
    private String iso;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "upper_name", nullable = false, length = 80)
    private String upperName;

    @Column(name = "iso3", length = 3)
    private String iso3;

    @Column(name = "num_code")
    private Short numCode;

    @Column(name = "phone_code", nullable = false)
    private Integer phoneCode;

    @ManyToMany(mappedBy = "countries")
    private Set<ShippingZone> shippingZones = new HashSet<>();

    public Country(String iso, String name, String upperName, String iso3, Short numCode, Integer phoneCode) {
        this.iso = iso;
        this.name = name;
        this.upperName = upperName;
        this.iso3 = iso3;
        this.numCode = numCode;
        this.phoneCode = phoneCode;
    }
    public Country() {

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpperName() {
        return upperName;
    }

    public void setUpperName(String upperName) {
        this.upperName = upperName;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Short getNumCode() {
        return numCode;
    }

    public void setNumCode(Short numCode) {
        this.numCode = numCode;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public Set<ShippingZone> getShippingZones() {
        return shippingZones;
    }

    public void setShippingZones(Set<ShippingZone> shippingZones) {
        this.shippingZones = shippingZones;
    }
}
