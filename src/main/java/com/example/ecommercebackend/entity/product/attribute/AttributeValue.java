package com.example.ecommercebackend.entity.product.attribute;

import com.example.ecommercebackend.entity.user.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

/*
Amaç: Her özelliğin (attribute) sahip olabileceği değerleri tutar. Örneğin, bir ürünün "Renk" özelliği için "Kırmızı", "Mavi" gibi değerler olabilir.

Kolonlar:

id: Her özellik değeri için benzersiz kimlik.

attribute_id: Hangi özellik için bu değerin geçerli olduğunu belirtir. Bu, attributes tablosundaki id ile ilişkilidir.

attribute_value: Özelliğin değeri (örneğin, "Kırmızı", "Büyük", "S" gibi).

color: Eğer özellik değeri görsel bir öğe ise (örneğin, renk), burada görsel temsil için renk bilgisi saklanabilir.

İlişkiler: Bu tablo, attributes tablosu ile bir ManyToOne ilişki kurar. Bir özellik birden fazla değere sahip olabilir.
 */

@Entity
@Table(name = "attribute_value")
public class AttributeValue {   // renk için kırmızı, yeşil
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_value_seq")
    @SequenceGenerator(name = "attribute_value_seq", sequenceName = "attribute_value_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    private Attribute attribute;

    @Column(name = "value")
    private String value;

    @Column(name = "create_at")
    private Instant createdAt = Instant.now();

    @Column(name = "update_at")
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

    public AttributeValue(Attribute attribute, String value, Admin createdBy, Admin updatedBy) {
        this.attribute = attribute;
        this.value = value;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public AttributeValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
