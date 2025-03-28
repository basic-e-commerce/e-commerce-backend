package com.example.ecommercebackend.repository.product.shipping;

import com.example.ecommercebackend.entity.product.shipping.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByIso3(String iso3);
}
