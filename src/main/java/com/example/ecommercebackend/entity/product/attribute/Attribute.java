package com.example.ecommercebackend.entity.product.attribute;

import com.example.ecommercebackend.entity.user.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/*
Amaç: Ürünlerin sahip olabileceği özellikleri tanımlar.

Kolonlar:

id: Her özelliğin benzersiz kimliğini tutar (UUID formatında).

attribute_name: Özelliğin adı. Örneğin, "Renk", "Boyut" gibi.

İlişkiler: Bu tablo, ürünlerin hangi özelliklere sahip olduğunu tanımlar. Yani, burada bir ürün için renk, boyut gibi özellikler yer alır.

*/

@Entity
@Table(name = "attribute")
public class Attribute {   // renk , beden
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_seq")
    @SequenceGenerator(name = "attribute_seq", sequenceName = "attribute_seq", allocationSize = 1)
    private int id;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "create_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Admin createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Attribute(String attributeName, Admin createdBy, Admin updatedBy) {
        this.attributeName = attributeName;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Attribute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
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
