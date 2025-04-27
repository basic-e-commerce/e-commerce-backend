package com.example.ecommercebackend.controller.product.card;

import com.example.ecommercebackend.dto.product.card.CardProductRequestDto;
import com.example.ecommercebackend.dto.product.card.CardResponseDetails;
import com.example.ecommercebackend.service.product.card.CardItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card-item")
public class CardItemController {
    private final CardItemService cardItemService;

    public CardItemController(CardItemService cardItemService) {
        this.cardItemService = cardItemService;
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<CardResponseDetails>> getDetails(@RequestBody List<CardProductRequestDto> cardProductRequestDtos){
        return new ResponseEntity<>(cardItemService.getDetails(cardProductRequestDtos), HttpStatus.OK);
    }


}
