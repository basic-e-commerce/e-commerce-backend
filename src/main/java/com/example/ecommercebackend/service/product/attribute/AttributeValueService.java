package com.example.ecommercebackend.service.product.attribute;

import com.example.ecommercebackend.dto.product.attribute.attributevalue.AttributeValueCreateDto;
import com.example.ecommercebackend.entity.product.attribute.Attribute;
import com.example.ecommercebackend.entity.product.attribute.AttributeValue;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.attribute.AttributeValueRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.util.List;

@Service
public class AttributeValueService {
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeService attributeService;

    public AttributeValueService(AttributeValueRepository attributeValueRepository, AttributeService attributeService) {
        this.attributeValueRepository = attributeValueRepository;
        this.attributeService = attributeService;
    }

    public AttributeValue createAttributeValue(AttributeValueCreateDto attributeValueCreateDto) {
        if (attributeValueCreateDto.getName().isEmpty())
            throw new BadRequestException("Attribute Value" + ExceptionMessage.NOT_FOUND.getMessage());

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Attribute attribute = attributeService.findAttributeById(attributeValueCreateDto.getAttributeId());
            AttributeValue attributeValue = new AttributeValue(attribute,attributeValueCreateDto.getName(),admin,admin);
            return attributeValueRepository.save(attributeValue);
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }

    public AttributeValue findAttributeValueById(Integer attributeValueId) {
        return attributeValueRepository.findById(attributeValueId).orElseThrow(()-> new NotFoundException("Attribute Value " + ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public List<AttributeValue> findAttributeValueByAttribute(Integer attributeId) {
        Attribute attribute = attributeService.findAttributeById(attributeId);
        return attributeValueRepository.findByAttribute(attribute);
    }
}
