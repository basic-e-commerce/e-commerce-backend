package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.product.products.ProductTemplateBuilder;
import com.example.ecommercebackend.dto.product.products.productTemplate.ProductTemplateDto;
import com.example.ecommercebackend.dto.product.products.productTemplate.ProductTemplateResponseDto;
import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.products.ProductTemplateRepository;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTemplateService {
    private static final Logger log = LoggerFactory.getLogger(ProductTemplateService.class);
    private final ProductTemplateRepository productTemplateRepository;
    private final ProductTemplateBuilder productTemplateBuilder;

    public ProductTemplateService(ProductTemplateRepository productTemplateRepository, ProductTemplateBuilder productTemplateBuilder) {
        this.productTemplateRepository = productTemplateRepository;
        this.productTemplateBuilder = productTemplateBuilder;
    }

    public ProductTemplateResponseDto createProductTemplate(@NotNullParam ProductTemplateDto productTemplateDto) {
        if (existByName(productTemplateDto.getName())) {
            throw new ResourceAlreadyExistException("Bu isme sahip product template kullanılmaktadır.");
        }

        ProductTemplate productTemplate = productTemplateBuilder.productTemplateDtoToProductTemplate(productTemplateDto);
        return productTemplateBuilder.productTemplateToProductTemplateResponseDto(productTemplateRepository.save(productTemplate));
    }

    public List<ProductTemplateResponseDto> listProductTemplates() {
        return productTemplateRepository.findAll().stream().map(productTemplateBuilder::productTemplateToProductTemplateResponseDto).collect(Collectors.toList());
    }


    public Boolean existByName(String name) {
        return productTemplateRepository.existsByName(name);
    }

    public ProductTemplate findById(Integer productTemplateId) {
        return productTemplateRepository.findById(productTemplateId).orElseThrow(()-> {
            log.error("Id Bulunamadı product template findById(): "+ productTemplateId);
            return new NotFoundException("Product Template Bulunamadı");
        });
    }
}
