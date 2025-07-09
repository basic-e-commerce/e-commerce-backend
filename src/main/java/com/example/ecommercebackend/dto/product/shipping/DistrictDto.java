package com.example.ecommercebackend.dto.product.shipping;

public class DistrictDto {
    private String name;
    private String districtID;
    private String cityCode;
    private String regionCode;
    private String countryCode;

    public DistrictDto(String name, String districtID, String cityCode, String regionCode, String countryCode) {
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

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
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
