package com.example.ecommercebackend.service.product.attribute;

import com.example.ecommercebackend.dto.product.attribute.AttributeCreateDto;
import com.example.ecommercebackend.entity.product.attribute.Attribute;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.attribute.AttributeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.util.List;

@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute createAttribute(AttributeCreateDto attributeCreateDto) {
        if (attributeCreateDto.getName().isEmpty())
            throw new BadRequestException("Attribute name" + ExceptionMessage.NOT_FOUND.getMessage() );

        if (attributeRepository.existsByAttributeNameEqualsIgnoreCase(attributeCreateDto.getName()))
            throw new ResourceAlreadyExistException("Attribute " + ExceptionMessage.ALREADY_EXISTS.getMessage());

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Attribute attribute = new Attribute(attributeCreateDto.getName(), admin,admin);
            return attributeRepository.save(attribute);
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }

    public Attribute findAttributeById(int id) {
        return attributeRepository.findById(id).orElseThrow(()-> new NotFoundException("Attribute "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }
}
