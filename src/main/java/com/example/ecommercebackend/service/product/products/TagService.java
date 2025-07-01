package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.dto.product.products.tag.TagCreateDto;
import com.example.ecommercebackend.entity.product.products.Tag;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.products.TagRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(TagCreateDto tagCreateDto) {
        if (tagRepository.existsByTagNameEqualsIgnoreCase(tagCreateDto.getTagName()))
            throw new ResourceAlreadyExistException("Tag "+ ExceptionMessage.ALREADY_EXISTS.getMessage());

        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin admin){
            Tag tag = new Tag(tagCreateDto.getTagName(), admin,admin);
            return tagRepository.save(tag);
        }else
            throw new BadRequestException("Authenticated user is not an Admin.");
    }


    public String deleteTag(Integer id) {
        Tag tag = findTagById(id);

        if (!tag.getProducts().isEmpty())
            throw new BadRequestException("Tag already has products.");

        tagRepository.delete(tag);
        return tag.getTagName() +" deleted successfully";
    }

    public Tag findTagById(Integer id) {
        return tagRepository.findById(id).orElseThrow(()-> new NotFoundException("Tag "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }
}
