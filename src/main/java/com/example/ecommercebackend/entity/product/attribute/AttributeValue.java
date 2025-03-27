package com.example.ecommercebackend.entity.product.attribute;

import jakarta.persistence.*;

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
public class AttributeValue {   // renk için kırmızı, yeşil
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_value_seq")
    @SequenceGenerator(name = "attribute_value_seq", sequenceName = "attribute_value_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    private Attribute attribute;


    // Attribute'ün değeri
    private String value;

    public AttributeValue(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;
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
}
