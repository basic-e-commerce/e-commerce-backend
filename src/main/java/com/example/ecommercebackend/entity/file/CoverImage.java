package com.example.ecommercebackend.entity.file;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class CoverImage extends Image {

    private String url;

    public CoverImage(String name, Long size, String resolution, String format, String url) {
        super(name, size, resolution, format);
        this.url = url;
    }

    public CoverImage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
