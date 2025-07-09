package com.example.ecommercebackend.dto.product.shipping;

public class CityDto {
    private String name;
    private String areaCode;
    private String cityCode;
    private String countryCode;

    public CityDto(String name, String areaCode, String cityCode, String countryCode) {
        this.name = name;
        this.areaCode = areaCode;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

    public CityDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
