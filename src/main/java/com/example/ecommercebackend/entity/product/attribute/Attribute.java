package com.example.ecommercebackend.entity.product.attribute;

import com.example.ecommercebackend.entity.user.Admin;
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
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_seq")
    @SequenceGenerator(name = "attribute_seq", sequenceName = "attribute_seq", allocationSize = 1)
    private int id;

    private String attributeName;

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

    public Attribute(String attributeName, Instant createdAt, Instant updatedAt, Admin createdBy, Admin updatedBy) {
        this.attributeName = attributeName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
