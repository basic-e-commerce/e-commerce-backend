package com.example.ecommercebackend.entity.user;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_seq")
    @SequenceGenerator(name = "district_seq", sequenceName = "district_seq", allocationSize = 1)
    private int id;
    private String name;
    @Column(unique = true)
    private Integer districtId;
    private String cityCode;
    private String countryCode;

    public District(String name, Integer districtId, String cityCode, String countryCode) {
        this.name = name;
        this.districtId = districtId;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

    public District() {
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

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
