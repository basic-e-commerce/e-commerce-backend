package com.example.ecommercebackend.entity.product.variant;

import com.example.ecommercebackend.entity.product.attribute.ProductAttributeValue;
import jakarta.persistence.*;

@Entity
@Table(name = "variant_value")
public class VariantValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variant_value_seq")
    @SequenceGenerator(name = "variant_value_seq", sequenceName = "variant_value_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false)
    private Variant variant;

    @ManyToOne
    @JoinColumn(name = "product_attribute_value_id", referencedColumnName = "id", nullable = false)
    private ProductAttributeValue productAttributeValue;

    public VariantValue(Variant variant, ProductAttributeValue productAttributeValue) {
        this.variant = variant;
        this.productAttributeValue = productAttributeValue;
    }

    public VariantValue() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public ProductAttributeValue getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(ProductAttributeValue productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }
}
