package com.example.ecommercebackend.dto.file;

public class ImageDetailDto {
    private int id;
    private String name;
    private String resolution;
    private String alt;
    private String url;
    private Integer order;

    public ImageDetailDto(int id, String name, String resolution, String alt, String url, Integer order) {
        this.id = id;
        this.name = name;
        this.resolution = resolution;
        this.alt = alt;
        this.url = url;
        this.order = order;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
