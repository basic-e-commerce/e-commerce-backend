package com.example.ecommercebackend.controller.merchant;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.service.merchant.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<MerchantResponseDto> createMerchant(@ModelAttribute MerchantCreateDto merchantCreateDto) {
        return new ResponseEntity<>(merchantService.createMerchant(merchantCreateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MerchantResponseDto> updateMerchant(@RequestBody MerchantUpdateDto merchantCreateDto) {
        return new ResponseEntity<>(merchantService.updateMerchant(merchantCreateDto),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Merchant>> getMerchants() {
        return new ResponseEntity<>(merchantService.getMerchants(), HttpStatus.OK);
    }

    @PutMapping("/image")
    public ResponseEntity<ImageDetailDto> updateCoverImage(@RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(merchantService.updateCoverImage(file), HttpStatus.OK);
    }
}
