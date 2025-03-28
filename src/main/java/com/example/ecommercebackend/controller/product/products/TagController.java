package com.example.ecommercebackend.controller.product.products;

import com.example.ecommercebackend.dto.product.products.tag.TagCreateDto;
import com.example.ecommercebackend.entity.product.products.Tag;
import com.example.ecommercebackend.service.product.products.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagCreateDto tagCreateDto) {
        return new ResponseEntity<>(tagService.createTag(tagCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTag(@RequestParam Integer id) {
        return new ResponseEntity<>(tagService.deleteTag(id),HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Tag> findTagById(@RequestParam Integer id) {
        return new ResponseEntity<>(tagService.findTagById(id),HttpStatus.OK);
    }

}
