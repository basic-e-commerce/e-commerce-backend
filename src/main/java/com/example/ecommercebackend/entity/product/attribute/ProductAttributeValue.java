package com.example.ecommercebackend.entity.product.attribute;

import jakarta.persistence.*;

/*
Amaç: Ürünlerin her özellik için belirlenen değeri tutar.

Kolonlar:

id: Benzersiz kimlik (UUID formatında).

product_attribute_id: Hangi ürün ve özellik ilişkisine ait olduğunu belirtir. Bu, product_attributes tablosundaki id ile ilişkilidir.

attribute_value_id: Hangi özellik değerinin seçildiğini belirtir. Bu, attribute_values tablosundaki id ile ilişkilidir.

İlişkiler: Bu tablo, bir ürünün bir özelliğe ait belirli değerini saklar. Yani, ürün ve özellik ilişkisinin (product_attribute) belirli bir değeri (attribute_value) almasını sağlar.

Özet:
Attributes tablosu, ürünlerin sahip olabileceği özellikleri tanımlar.

Attribute_values tablosu, her özellik için olabilecek değerleri saklar.

Product_attributes tablosu, ürünler ve özellikler arasındaki ilişkiyi yönetir.

Product_attribute_values tablosu, ürünlerin özellik değerlerini saklar.
 */

@Entity
public class ProductAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_attribute_value_seq")
    @SequenceGenerator(name = "product_attribute_value_seq", sequenceName = "product_attribute_value_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id", referencedColumnName = "id")
    private ProductAttribute productAttribute;

    @ManyToOne
    @JoinColumn(name = "attribute_value_id", referencedColumnName = "id")
    private AttributeValue attributeValue;

    public ProductAttributeValue(ProductAttribute productAttribute, AttributeValue attributeValue) {
        this.productAttribute = productAttribute;
        this.attributeValue = attributeValue;
    }

    public ProductAttributeValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }
}
