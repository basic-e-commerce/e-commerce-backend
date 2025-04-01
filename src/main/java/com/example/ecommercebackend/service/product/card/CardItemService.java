package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.dto.product.card.CardItemCreateDto;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.card.CardItemRepository;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardItemService {
    private final CardItemRepository cardItemRepository;
    private final ProductRepository productRepository;

    public CardItemService(CardItemRepository cardItemRepository, ProductRepository productRepository) {
        this.cardItemRepository = cardItemRepository;
        this.productRepository = productRepository;
    }

    public CardItem create(CardItemCreateDto cardItemCreateDto) {
        Product product = productRepository.findById(cardItemCreateDto.getProductId()).orElseThrow(()-> new NotFoundException("Product not found card item"));
        CardItem cardItem = new CardItem(product, cardItemCreateDto.getQuantity());
        return cardItemRepository.save(cardItem);
    }

    public CardItem save(CardItem cardItem){
        return cardItemRepository.save(cardItem);
    }

    public void delete(CardItem cardItem) {
        cardItemRepository.delete(cardItem);
    }
}
