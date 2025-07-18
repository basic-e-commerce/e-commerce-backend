package com.example.ecommercebackend.entity.product.products;

import com.example.ecommercebackend.entity.user.Admin;
import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;

@Entity
@Table(name = "product_template")
public class ProductTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_template_seq")
    @SequenceGenerator(name = "product_template_seq", sequenceName = "product_template_seq", allocationSize = 1)
    private int id;

    private String geliverId;

    private String name;
    private Double length;  // uzunluk
    private Double width;  // genişlik
    private Double height;  // yükseklik
    private Double desi;    // hacim
    @Enumerated(EnumType.STRING)
    private DistanceUnit distanceUnit;   // uzunluk tipi
    private Double weight;    // ürünün ağırlıpı
    @Enumerated(EnumType.STRING)
    private MassUnit massUnit;   // ağırlık tipi
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;

    public ProductTemplate(String name, Double length, Double width, Double height, DistanceUnit distanceUnit, Double weight, MassUnit massUnit, Boolean isActive) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.desi = (length*width*height);
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.massUnit = massUnit;
        this.isActive = isActive;
    }

    public ProductTemplate() {
    }


    @PrePersist
    private void generateProductData() {
        Instant now = Instant.now();
        this.updatedAt = now;
        this.createdAt = now;
    }

    @PreUpdate
    private void updateProductData() {
        this.updatedAt = Instant.now();
    }

    public enum DistanceUnit {
        mm("mm"),
        cm("cm"),
        m("m"),
        in("in");

        private final String code;

        DistanceUnit(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static DistanceUnit fromCode(String code) {
            for (DistanceUnit unit : values()) {
                if (unit.code.equalsIgnoreCase(code)) {
                    return unit;
                }
            }
            throw new IllegalArgumentException("Geçersiz mesafe birimi: " + code);
        }
    }

    public enum  MassUnit {
        mg("mg"),
        g("g"),
        kg("kg"),
        lb("lb");

        private final String code;

        MassUnit(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static MassUnit fromCode(String code) {
            for (MassUnit unit : values()) {
                if (unit.code.equalsIgnoreCase(code)) {
                    return unit;
                }
            }
            throw new IllegalArgumentException("Geçersiz kütle birimi: " + code);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeliverId() {
        return geliverId;
    }

    public void setGeliverId(String geliverId) {
        this.geliverId = geliverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDesi() {
        return desi;
    }

    public void setDesi(Double desi) {
        this.desi = desi;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(MassUnit massUnit) {
        this.massUnit = massUnit;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
}
