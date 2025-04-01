package com.example.ecommercebackend.entity.product.card;


import com.example.ecommercebackend.entity.product.products.Product;
import jakarta.persistence.*;

/*
CardItem sınıfı, sepetin içindeki ürünleri tutar. Bir sepetin birden fazla CardItem'ı olabilir, çünkü bir müşteri sepetine birden fazla ürün ekleyebilir. Her CardItem, bir Product nesnesini temsil eder ve bu ürünün sepetteki miktarını tutar.

quantity: Sepet item'ındaki ürün sayısını belirtir. Eğer müşteri bir üründen birden fazla almak istiyorsa, bu miktar arttırılabilir.

İlişki Yapıları:

Card ile ilişki (card_id): Bir sepet birden fazla CardItem içerebilir. Bu, CardItem tablosunda card_id alanı ile sağlanır.

Product ile ilişki (product_id): Her CardItem, bir Product nesnesine işaret eder. Bu da, sepetin içinde hangi ürünlerin bulunduğunu belirtir.
 */
@Entity
@Table(name = "card_items")
public class CardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity = 0;

    public CardItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public CardItem() {

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
