package com.example.ecommercebackend.entity.user;

import com.example.ecommercebackend.entity.product.shipping.Country;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private int id;

    private String title;
    @ManyToOne
    private Country country;

}
