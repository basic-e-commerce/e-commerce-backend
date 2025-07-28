package com.example.ecommercebackend.entity.product.order;


import com.example.ecommercebackend.entity.product.products.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "price", nullable = false)
    private BigDecimal price;  // içerideki ürünleri compare fiyatıyla miktarın çarpımı sonucu oluşan fiyat

    @Column(name = "discount_price")
    private BigDecimal discountPrice;   // kupon vs kullanıldıktan sonra

    private BigDecimal substractDiscountPrice = BigDecimal.ZERO;  // toplam indirim yapılan tutar

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_refund",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRefund = false;
    @Column(name = "is_cancel",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCancel = false;

    public OrderItem(Product product, BigDecimal price, Integer quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getSubstractDiscountPrice() {
        return substractDiscountPrice;
    }

    public void setSubstractDiscountPrice(BigDecimal substractDiscountPrice) {
        this.substractDiscountPrice = substractDiscountPrice;
    }

    public Boolean getRefund() {
        return isRefund;
    }

    public void setRefund(Boolean refund) {
        isRefund = refund;
    }

    public Boolean getCancel() {
        return isCancel;
    }

    public void setCancel(Boolean cancel) {
        isCancel = cancel;
    }
}
