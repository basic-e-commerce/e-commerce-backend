package com.example.ecommercebackend.entity.merchant;

import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.user.Address;
import jakarta.persistence.*;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_seq")
    @SequenceGenerator(name = "merchant_seq", sequenceName = "merchant_seq", allocationSize = 1)
    private int id;
    private String name;
    @OneToOne
    private Address address;

    @OneToOne
    private CoverImage coverImage;
    private String phone;
    private String email;

    public Merchant(String name, Address address, CoverImage coverImage, String phone, String email) {
        this.name = name;
        this.address = address;
        this.coverImage = coverImage;
        this.phone = phone;
        this.email = email;
    }
    public Merchant() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
