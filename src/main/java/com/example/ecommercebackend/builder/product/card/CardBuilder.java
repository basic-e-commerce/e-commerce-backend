package com.example.ecommercebackend.builder.product.card;

import com.example.ecommercebackend.dto.product.card.CardItemResponseDto;
import com.example.ecommercebackend.dto.product.card.CardResponseDto;
import com.example.ecommercebackend.entity.product.card.Card;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CardBuilder {

    public CardResponseDto cardToCardResponseDto(Card card) {
        return new CardResponseDto(card.getId(),
                card.getCustomer().getFirstName()+" "+card.getCustomer().getLastName(),
                card.getItems().stream().map(x->{
                    return new CardItemResponseDto(x.getProduct().getId(),x.getProduct().getProductName(),x.getProduct().getComparePrice(),x.getQuantity());
                }).toList()
        );
    }
}
