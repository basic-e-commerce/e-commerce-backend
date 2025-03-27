package com.example.ecommercebackend.entity.product.variant;


import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.product.products.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
/*
 bir ürünün varyantlarının seçeneklerini saklar
 Örneğin bir tshirtün  farklı renklerde veya farklı bedenlerde olabilir
 */
@Entity
@Table(name = "variant_options")
public class VariantOption {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variant_option_seq")
    @SequenceGenerator(name = "variant_option_seq", sequenceName = "variant_option_seq", allocationSize = 1)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "cover_image_id", referencedColumnName = "id", nullable = true)
    private CoverImage coverImage;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(name = "sale_price", nullable = false, columnDefinition = "NUMERIC DEFAULT 0")
    private BigDecimal salePrice;

    @Column(name = "compare_price", columnDefinition = "NUMERIC DEFAULT 0")
    private BigDecimal comparePrice;

    @Column(name = "buying_price", columnDefinition = "NUMERIC DEFAULT NULL")
    private BigDecimal buyingPrice;

    @Column(name = "quantity", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer quantity;

    @Column(name = "sku")
    private String sku;

    @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    public VariantOption(String title, CoverImage coverImage, Product product, BigDecimal salePrice, BigDecimal comparePrice, BigDecimal buyingPrice, Integer quantity, String sku, Boolean active) {
        this.title = title;
        this.coverImage = coverImage;
        this.product = product;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.buyingPrice = buyingPrice;
        this.quantity = quantity;
        this.sku = sku;
        this.active = active;
    }

    public VariantOption() {}

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

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(BigDecimal comparePrice) {
        this.comparePrice = comparePrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
