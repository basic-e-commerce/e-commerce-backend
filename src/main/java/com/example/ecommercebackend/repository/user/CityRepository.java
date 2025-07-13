package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {
    Optional<City> findByName(String name);
    Optional<City> findByCityCode(String cityCode);
    Boolean existsByCityCode(String cityCode);
    void deleteByCityCode(String cityCode);
}
