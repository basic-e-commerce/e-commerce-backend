package com.example.ecommercebackend.controller.merchant;

import com.example.ecommercebackend.anotation.RateLimit;
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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    @RateLimit(limit = 1, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<MerchantResponseDto> createMerchant(@ModelAttribute MerchantCreateDto merchantCreateDto) {
        return new ResponseEntity<>(merchantService.createMerchant(merchantCreateDto), HttpStatus.CREATED);
    }

    @PutMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<MerchantResponseDto> updateMerchant(@RequestBody MerchantUpdateDto merchantCreateDto) {
        return new ResponseEntity<>(merchantService.updateMerchant(merchantCreateDto),HttpStatus.OK);
    }

    @GetMapping
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<Merchant>> getMerchants() {
        return new ResponseEntity<>(merchantService.getMerchants(), HttpStatus.OK);
    }

    @PutMapping("/image")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<ImageDetailDto> updateCoverImage(@RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(merchantService.updateCoverImage(file), HttpStatus.OK);
    }
}
