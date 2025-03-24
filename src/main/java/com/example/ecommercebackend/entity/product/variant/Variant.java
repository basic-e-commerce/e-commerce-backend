package com.example.ecommercebackend.entity.product.variant;

import com.example.ecommercebackend.entity.product.products.Product;
import jakarta.persistence.*;


@Entity
@Table(name = "variants")
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variant_seq")
    @SequenceGenerator(name = "variant_seq", sequenceName = "variant_seq", allocationSize = 1)
    private int id;

    @Column(name = "variant_option", nullable = false)
    private String variantOption;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variant_option_id", referencedColumnName = "id", nullable = false)
    private VariantOption variantOptionEntity;

    public Variant(String variantOption, Product product, VariantOption variantOptionEntity) {
        this.variantOption = variantOption;
        this.product = product;
        this.variantOptionEntity = variantOptionEntity;
    }

    public Variant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVariantOption() {
        return variantOption;
    }

    public void setVariantOption(String variantOption) {
        this.variantOption = variantOption;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public VariantOption getVariantOptionEntity() {
        return variantOptionEntity;
    }

    public void setVariantOptionEntity(VariantOption variantOptionEntity) {
        this.variantOptionEntity = variantOptionEntity;
    }
}
