package com.example.ecommercebackend.entity.product.shipping;

import jakarta.persistence.*;

/**
 * Bu tablo, her bir nakliye bölgesi için geçerli olan taşıma ücret tarifelerini saklar. Bu tarifeler, bölgedeki ürünlerin taşınma maliyetlerini belirler. Fiyatlandırma, ürünlerin ağırlığı ve değerine göre yapılabilir.
 *
 * Mantık:
 * shipping_zone_id: Bu sütun, ücret tarifesinin hangi nakliye bölgesine ait olduğunu belirler. Bir bölgeye ait birden fazla tarifeyi bu tablodan alabiliriz.
 *
 * weight_unit: Ücretin nasıl hesaplanacağını belirtir. 'g' (gram) veya 'kg' (kilogram) gibi seçenekler sunar.
 *
 * min_value ve max_value: Ürünün ağırlığı, fiyatı veya diğer ölçütleri bu aralıklara göre değerlendirilebilir. Örneğin, belirli bir aralıktaki ağırlık için bir ücret tarifesi uygulanır.
 *
 * no_max: Bu, belirli bir üst sınır olup olmadığını belirtir. Eğer max_value boş ise, bu özellik TRUE olarak kabul edilir ve belirli bir ağırlık sınırı olmadan ücret hesaplanır.
 *
 * price: Ürünün nakliye fiyatını belirler. Bu fiyat, nakliye bölgesi ve ücret tarifesinin kriterlerine göre hesaplanır.
 */

@Entity
@Table(name = "shipping_rates")
public class ShippingRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_rate_seq")
    @SequenceGenerator(name = "shipping_rate_seq", sequenceName = "shipping_rate_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shipping_zone_id", referencedColumnName = "id")
    private ShippingZone shippingZone;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private WeightUnit weightUnit;

    private Double minValue = 0.0;
    private Double maxValue = null;
    private Boolean noMax = true;
    private Double price = 0.0;

    public enum WeightUnit {
        G, KG
    }

    public ShippingRate(ShippingZone shippingZone, WeightUnit weightUnit, Double minValue, Double maxValue, Boolean noMax, Double price) {
        this.shippingZone = shippingZone;
        this.weightUnit = weightUnit;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.noMax = noMax;
        this.price = price;
    }

    public ShippingRate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShippingZone getShippingZone() {
        return shippingZone;
    }

    public void setShippingZone(ShippingZone shippingZone) {
        this.shippingZone = shippingZone;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Boolean getNoMax() {
        return noMax;
    }

    public void setNoMax(Boolean noMax) {
        this.noMax = noMax;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
