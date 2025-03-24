package com.example.ecommercebackend.entity.product.shipping;

import com.example.ecommercebackend.entity.product.products.Product;
import jakarta.persistence.*;

/**
 *  product_shipping_info (Ürün Nakliye Bilgisi)
 * Bu tablo, her bir ürün için nakliye ile ilgili bilgileri tutar. Buradaki ana amacımız, bir ürünün taşınabilirliğine dair verileri toplamak. Örneğin, bir ürünün ağırlığı, hacmi, boyutları gibi bilgiler burada saklanır.
 *
 * Mantık:
 * product_id: Bu sütun, ürünle ilişkilendirilir. Yani her ürünün kendine özgü bir nakliye bilgisi vardır. Bu, bir ManyToOne ilişkisi ile Product tablosuna bağlanır.
 *
 * weight, volume, dimension: Ürünün taşınabilirliğini belirleyen fiziksel özellikler. Bu bilgilerin her biri, taşıma maliyetlerini ve koşullarını belirlemek için kullanılır (örneğin, daha ağır ve büyük ürünlerin taşınması daha pahalı olabilir).
 *
 * Bir ürün taşınabilirken, bu bilgiler taşınacak ürüne özgü olarak işlenir. Eğer ürün silinirse veya nakliye bilgileri güncellenirse, "ON DELETE SET NULL" ifadesi sayesinde, ürünle ilişkilendirilmiş nakliye bilgisi sıfırlanır, fakat nakliye bilgisi tablodan silinmez.
 */

@Entity
@Table(name = "product_shipping_info")
public class ProductShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_shipping_info_seq")
    @SequenceGenerator(name = "product_shipping_info_seq", sequenceName = "product_shipping_info_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true)
    private Product product;

    private Double weight = 0.0;

    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    private Double volume = 0.0;

    @Enumerated(EnumType.STRING)
    private VolumeUnit volumeUnit;

    private Double dimensionWidth = 0.0;
    private Double dimensionHeight = 0.0;
    private Double dimensionDepth = 0.0;

    @Enumerated(EnumType.STRING)
    private DimensionUnit dimensionUnit;

    public enum WeightUnit {
        G, KG
    }

    public enum VolumeUnit {
        L, ML
    }

    public enum DimensionUnit {
        L, ML
    }

    public ProductShippingInfo(Product product, Double weight, WeightUnit weightUnit, Double volume, VolumeUnit volumeUnit, Double dimensionWidth, Double dimensionHeight, Double dimensionDepth, DimensionUnit dimensionUnit) {
        this.product = product;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.volume = volume;
        this.volumeUnit = volumeUnit;
        this.dimensionWidth = dimensionWidth;
        this.dimensionHeight = dimensionHeight;
        this.dimensionDepth = dimensionDepth;
        this.dimensionUnit = dimensionUnit;
    }

    public ProductShippingInfo() {

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public VolumeUnit getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(VolumeUnit volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public Double getDimensionWidth() {
        return dimensionWidth;
    }

    public void setDimensionWidth(Double dimensionWidth) {
        this.dimensionWidth = dimensionWidth;
    }

    public Double getDimensionHeight() {
        return dimensionHeight;
    }

    public void setDimensionHeight(Double dimensionHeight) {
        this.dimensionHeight = dimensionHeight;
    }

    public Double getDimensionDepth() {
        return dimensionDepth;
    }

    public void setDimensionDepth(Double dimensionDepth) {
        this.dimensionDepth = dimensionDepth;
    }

    public DimensionUnit getDimensionUnit() {
        return dimensionUnit;
    }

    public void setDimensionUnit(DimensionUnit dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
    }
}
