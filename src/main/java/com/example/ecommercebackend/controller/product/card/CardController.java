package com.example.ecommercebackend.controller.product.card;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.card.CardCreateDto;
import com.example.ecommercebackend.dto.product.card.CardResponseDto;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.service.product.card.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PutMapping
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<CardResponseDto> updateCard(@RequestBody CardCreateDto cardCreateDto) {
        return new ResponseEntity<>(cardService.updateCard(cardCreateDto), HttpStatus.OK);
    }

    @PutMapping("/add-coupon")
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> addCoupon(@RequestParam String code) {
        return new ResponseEntity<>(cardService.addCoupon(code),HttpStatus.OK);
    }

    @PutMapping("/remove-coupon")
    @RateLimit(limit = 10, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> removeCoupon() {
        return new ResponseEntity<>(cardService.removeCoupon(),HttpStatus.OK);
    }

}
