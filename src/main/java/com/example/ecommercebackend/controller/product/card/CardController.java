package com.example.ecommercebackend.controller.product.card;

import com.example.ecommercebackend.dto.product.card.CardCreateDto;
import com.example.ecommercebackend.dto.product.card.CardResponseDto;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.service.product.card.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PutMapping
    public ResponseEntity<CardResponseDto> updateCard(@RequestBody CardCreateDto cardCreateDto) {
        return new ResponseEntity<>(cardService.updateCard(cardCreateDto), HttpStatus.OK);
    }
}
