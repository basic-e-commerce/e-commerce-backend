package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistrictDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("districtID")
    private Integer districtID;
    @JsonProperty("cityCode")
    private String cityCode;
    @JsonProperty("regionCode")
    private String regionCode;
    @JsonProperty("countryCode")
    private String countryCode;

    public DistrictDto(String name, Integer districtID, String cityCode, String regionCode, String countryCode) {
        this.name = name;
        this.districtID = districtID;
        this.cityCode = cityCode;
        this.regionCode = regionCode;
        this.countryCode = countryCode;
    }

    public DistrictDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
