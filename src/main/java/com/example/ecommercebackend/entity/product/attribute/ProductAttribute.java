package com.example.ecommercebackend.entity.product.attribute;

import com.example.ecommercebackend.entity.product.products.Product;
import jakarta.persistence.*;

/*
Amaç: Ürünler ve özellikler arasında ilişki kurar. Bu tablo, hangi ürünlerin hangi özelliklere sahip olduğunu belirler.

Kolonlar:

id: Her ilişkiyi benzersiz kılar (UUID formatında).

product_id: Hangi ürüne ait olduğunu belirtir. Bu, products tablosundaki id ile ilişkilidir.

attribute_id: Hangi özelliği ifade ettiğini belirtir. Bu, attributes tablosundaki id ile ilişkilidir.

İlişkiler: Bu tablo, bir ürün ve bir özellik arasındaki ilişkiyi saklar. Bu da bir ManyToMany ilişkiyi temsil eder çünkü bir ürün birden fazla özelliğe sahip olabilir ve bir özellik birden fazla üründe yer alabilir.
 */
@Entity
public class ProductAttribute {   // bir ürünün hangi özelliklere sahip oldugunu belirtir renk , beden vs
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_attribute_seq")
    @SequenceGenerator(name = "product_attribute_seq", sequenceName = "product_attribute_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    private Attribute attribute;

    public ProductAttribute(Product product, Attribute attribute) {
        this.product = product;
        this.attribute = attribute;
    }

    public ProductAttribute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
