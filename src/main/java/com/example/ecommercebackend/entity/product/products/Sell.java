package com.example.ecommercebackend.entity.product.products;

import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "sells")
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sells_seq")
    @SequenceGenerator(name = "sells_seq", sequenceName = "sells_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;  // Burada Product entity'si ile ilişkilendirdik

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    private Instant sellDate = Instant.now();

    public Sell(Product product, BigDecimal price, Integer quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Sell() {

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
        if (quantity < 0)
            throw new BadRequestException("Quantity "+ ExceptionMessage.NOT_NEGATIVE.getMessage());
        this.quantity = quantity;
    }

    public Instant getSellDate() {
        return sellDate;
    }

    public void setSellDate(Instant sellDate) {
        this.sellDate = sellDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
