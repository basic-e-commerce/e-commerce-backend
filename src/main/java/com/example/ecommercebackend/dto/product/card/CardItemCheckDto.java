package com.example.ecommercebackend.dto.product.card;

import com.example.ecommercebackend.entity.product.card.CardItem;

import java.util.List;

public class CardItemCheckDto {
    private List<CardItem> cardItems;
    private Boolean isChange;

    public CardItemCheckDto(List<CardItem> cardItems, Boolean isChange) {
        this.cardItems = cardItems;
        this.isChange = isChange;
    }

    public List<CardItem> getCardItems() {
        return cardItems;
    }

    public void setCardItems(List<CardItem> cardItems) {
        this.cardItems = cardItems;
    }

    public Boolean getChange() {
        return isChange;
    }

    public void setChange(Boolean change) {
        isChange = change;
    }
}
