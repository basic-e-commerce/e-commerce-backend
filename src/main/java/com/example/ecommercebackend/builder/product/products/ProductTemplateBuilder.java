package com.example.ecommercebackend.builder.product.products;

import com.example.ecommercebackend.dto.product.products.productTemplate.ProductTemplateDto;
import com.example.ecommercebackend.dto.product.products.productTemplate.ProductTemplateResponseDto;
import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductTemplateBuilder {

    public ProductTemplate productTemplateDtoToProductTemplate(ProductTemplateDto dto) {
        return new ProductTemplate(
                dto.getName(),
                dto.getLength(),
                dto.getWidth(),
                dto.getHeight(),
                dto.getDistanceUnit(),
                dto.getWeight(),
                dto.getMassUnit(),
                dto.getActive()
        );
    }

    public ProductTemplateResponseDto productTemplateToProductTemplateResponseDto(ProductTemplate productTemplate) {
        return new ProductTemplateResponseDto(
                productTemplate.getName(),
                productTemplate.getLength(),
                productTemplate.getWidth(),
                productTemplate.getHeight(),
                productTemplate.getDesi(),
                productTemplate.getDistanceUnit(),
                productTemplate.getWeight(),
                productTemplate.getMassUnit(),
                productTemplate.getActive()
        );
    }
}
