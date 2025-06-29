package com.example.ecommercebackend.controller.product.card;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.product.card.CardProductRequestDto;
import com.example.ecommercebackend.dto.product.card.CardResponseDetail;
import com.example.ecommercebackend.dto.product.card.CardResponseDetails;
import com.example.ecommercebackend.service.product.card.CardItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/card-item")
public class CardItemController {
    private final CardItemService cardItemService;

    public CardItemController(CardItemService cardItemService) {
        this.cardItemService = cardItemService;
    }

    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    @PostMapping("/by-ids")
    public ResponseEntity<CardResponseDetail> getDetails(@RequestBody List<CardProductRequestDto> cardProductRequestDtos){
        return new ResponseEntity<>(cardItemService.getDetails(cardProductRequestDtos), HttpStatus.OK);
    }


}
