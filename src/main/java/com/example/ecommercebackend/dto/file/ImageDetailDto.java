package com.example.ecommercebackend.dto.file;

public class ImageDetailDto {
    private int id;
    private String url;
    private Integer order;

    public ImageDetailDto(int id, String url, Integer order) {
        this.id = id;
        this.url = url;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
