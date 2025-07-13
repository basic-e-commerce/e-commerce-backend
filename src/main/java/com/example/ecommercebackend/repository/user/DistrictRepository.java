package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.District;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District,Integer>, JpaSpecificationExecutor<District> {
    Optional<District> findByDistrictId(Integer districtId);
    Boolean existsByDistrictId(Integer districtId);
    List<District> findAllByCityCode(String cityCode, Sort sort);
    void  deleteByDistrictId(Integer districtId);
}
