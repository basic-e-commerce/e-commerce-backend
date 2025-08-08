package com.example.ecommercebackend.controller.merchant;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.merchant.MerchantPublicDetailResponse;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
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

//    @PostMapping
//    @RateLimit(limit = 1, duration = 1, unit = TimeUnit.SECONDS)
//    public ResponseEntity<MerchantResponseDto> createMerchant(@ModelAttribute MerchantCreateDto merchantCreateDto) {
//        return new ResponseEntity<>(merchantService.createMerchant(merchantCreateDto), HttpStatus.CREATED);
//    }

    @PutMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<MerchantResponseDto> updateMerchant(@RequestBody MerchantUpdateDto merchantCreateDto) {
        return new ResponseEntity<>(merchantService.updateMerchant(merchantCreateDto),HttpStatus.OK);
    }

    @GetMapping
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<MerchantResponseDto>> getMerchants() {
        return new ResponseEntity<>(merchantService.getMerchants(), HttpStatus.OK);
    }

    @GetMapping("/public-detail")
    public ResponseEntity<MerchantPublicDetailResponse> getDetail(){
        return new ResponseEntity<>(merchantService.getDetail(),HttpStatus.OK);
    }

    @PutMapping("/image")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<ImageDetailDto> updateCoverImage(@RequestParam("image") MultipartFile file) {
        return new ResponseEntity<>(merchantService.updateCoverImage(file), HttpStatus.OK);
    }

    @GetMapping("/list-sending-address")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<List<AddressDetailDto>> getSendingAddresses() {
        return new ResponseEntity<>(merchantService.getSendingAddresses(),HttpStatus.OK);
    }


    @PostMapping("/add-sending-address")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<AddressDetailDto> createSendingAddress(@RequestBody(required = false) AddressCreateDto addressCreateDto) {
        return new ResponseEntity<>(merchantService.createSendingAddress(addressCreateDto), HttpStatus.CREATED);
    }

    @PostMapping("/remove-sending-address")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> removeSendingAddress(@RequestParam(required = false) Integer addressId) {
        return new ResponseEntity<>(merchantService.removeSendingAddress(addressId), HttpStatus.CREATED);
    }

    @PutMapping("/select-default-address")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<AddressDetailDto> selectDefaultAddress(@RequestParam(required = false) Integer addressId) {
        return new ResponseEntity<>(merchantService.selectDefaultSendingAddress(addressId), HttpStatus.OK);
    }

    @PutMapping("/select-default-custom-cargo-contract")
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> selectDefaultCustomCargoContact(@RequestParam(required = false) Integer customContractId) {
        return new ResponseEntity<>(merchantService.selectDefaultCustomCargoContact(customContractId), HttpStatus.OK);
    }


}
