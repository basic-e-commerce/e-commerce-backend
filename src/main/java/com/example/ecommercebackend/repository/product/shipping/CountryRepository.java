package com.example.ecommercebackend.repository.product.shipping;

import com.example.ecommercebackend.entity.product.shipping.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
