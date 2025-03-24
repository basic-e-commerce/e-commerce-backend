package com.example.ecommercebackend.entity.merchant;

import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.user.Admin;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "slideshows")
public class Slideshow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slides_show_seq")
    @SequenceGenerator(name = "slides_show_seq", sequenceName = "slides_show_seq", allocationSize = 1)
    private int id;

    private String title;
    private String destinationUrl;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_image_id", referencedColumnName = "id")
    private CoverImage coverImage;

    private String description;
    private Integer displayOrder;
    private Boolean published = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Admin createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Slideshow(String title, String destinationUrl, CoverImage coverImage, String description, Integer displayOrder, Boolean published, Admin createdBy, Admin updatedBy) {
        this.title = title;
        this.destinationUrl = destinationUrl;
        this.coverImage = coverImage;
        this.description = description;
        this.displayOrder = displayOrder;
        this.published = published;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Slideshow() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
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
}
