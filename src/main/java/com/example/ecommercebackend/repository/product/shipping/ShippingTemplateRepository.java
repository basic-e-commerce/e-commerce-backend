package com.example.ecommercebackend.repository.product.shipping;

import com.example.ecommercebackend.entity.product.shipping.ShippingTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShippingTemplateRepository extends JpaRepository<ShippingTemplate,Integer>, JpaSpecificationExecutor<ShippingTemplate> {
    ShippingTemplate findByGeliverId(String geliverId);
    void deleteByGeliverId(String geliverId);
}
