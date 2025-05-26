package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.builder.product.card.CardBuilder;
import com.example.ecommercebackend.dto.product.card.*;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.card.CardRepository;
import com.example.ecommercebackend.service.product.products.ProductService;
import com.example.ecommercebackend.service.user.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardItemService cardItemService;
    private final CardBuilder cardBuilder;
    private final ProductService productService;

    public CardService(CardRepository cardRepository, CardItemService cardItemService, CardBuilder cardBuilder, ProductService productService) {
        this.cardRepository = cardRepository;
        this.cardItemService = cardItemService;
        this.cardBuilder = cardBuilder;
        this.productService = productService;
    }

    /*
    Senaryo şu şekilde olacak
    eğer kullanıcı giriş yapmışsa kullanıcının zaten bir sepeti vardır.
    Dışarıdan bize liste şeklinde productId ve quantity (eklenecek olan sayı) gelecek  + veya - şeklinde

     */

    public CardResponseDto updateCard(CardCreateDto cardCreateDto) {
        // 1. Kullanıcı kimlik doğrulamasını al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        // 2. Kullanıcının müşteri olup olmadığını kontrol et
        if (!(principal instanceof Customer customer)) {
            throw new BadRequestException("Authentication failed");
        }else
            System.out.println("Customerr  -------");

        // 3. Müşterinin sepetini al
        Card card = customer.getCard();

        for (CardItemCreateDto cardItemCreateDto : cardCreateDto.getCardItems()) {
            int productQuantity = productService.findProductById(cardItemCreateDto.productId()).getQuantity();

            // 4. Sepette aynı üründen olup olmadığını kontrol et
            CardItem existingCardItem = card.getItems().stream()
                    .filter(item -> item.getProduct().getId() == cardItemCreateDto.productId())
                    .findFirst()
                    .orElse(null);

            if (existingCardItem != null) {

                // 5. Ürün zaten sepette varsa miktarı güncelle
                int totalQuantity = existingCardItem.getQuantity() + cardItemCreateDto.quantity();

                if (totalQuantity > productQuantity)
                    throw new BadRequestException("Yetersiz Ürün Stoğu: "+existingCardItem.getProduct().getProductName());

                if (totalQuantity <= 0) {
                    // 6. Miktar sıfır veya negatifse, ürünü sepetten kaldır
                    System.out.println("ürün 0 ise");
                    card.getItems().remove(existingCardItem);
                    cardRepository.save(card);
                    cardItemService.delete(existingCardItem);
                } else {
                    // 7. Miktarı güncelle
                    existingCardItem.setQuantity(totalQuantity);
                    cardItemService.save(existingCardItem);
                    cardRepository.save(card);
                }
            } else {
                if (cardItemCreateDto.quantity() <= 0)
                    throw new BadRequestException("0 ve altında adet olamaz");

                if (cardItemCreateDto.quantity() > productQuantity)
                    throw new BadRequestException("Yetersiz Ürün Stoğu: "+cardItemCreateDto.productId());

                // 8. Ürün sepette yoksa, yeni CardItem oluştur ve sepete ekle
                CardItem newCardItem = cardItemService.create(cardItemCreateDto);
                card.getItems().add(newCardItem);
                cardRepository.save(card);
            }
        }

        // 9. Güncellenmiş sepeti kaydet ve döndür
        return cardBuilder.cardToCardResponseDto(card);
    }


    public Card findByCustomer(Customer customer) {
        return cardRepository.findByCustomer(customer).orElseThrow(()-> new NotFoundException("Geçerli Kullanıcı sepeti bulunamadı"));
    }

    public void save(Card card) {
        cardRepository.save(card);
    }
}
