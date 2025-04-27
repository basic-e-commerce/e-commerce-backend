package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.dto.product.card.CardItemCreateDto;
import com.example.ecommercebackend.dto.product.card.CardItemResponseDto;
import com.example.ecommercebackend.dto.product.card.CardProductRequestDto;
import com.example.ecommercebackend.dto.product.card.CardResponseDetails;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.card.CardItemRepository;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardItemService {
    private final CardItemRepository cardItemRepository;
    private final ProductRepository productRepository;

    public CardItemService(CardItemRepository cardItemRepository, ProductRepository productRepository) {
        this.cardItemRepository = cardItemRepository;
        this.productRepository = productRepository;
    }

    public CardItem create(CardItemCreateDto cardItemCreateDto) {
        Product product = productRepository.findById(cardItemCreateDto.productId()).orElseThrow(()-> new NotFoundException("Product not found card item"));
        CardItem cardItem = new CardItem(product, cardItemCreateDto.quantity());
        return cardItemRepository.save(cardItem);
    }



    public CardItem save(CardItem cardItem){
        return cardItemRepository.save(cardItem);
    }

    public void delete(CardItem cardItem) {
        cardItemRepository.delete(cardItem);
    }

    public List<CardResponseDetails> getDetails(List<CardProductRequestDto> cardProductRequestDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        if (principal instanceof Customer customer) {
            return customer.getCard().getItems().stream().map(x-> {
                String url= "";
                if(x.getProduct().getCoverImage() != null){
                    url= x.getProduct().getCoverImage().getUrl();
                }

                return new CardResponseDetails(x.getProduct().getId(),
                        x.getProduct().getProductName(),
                        x.getProduct().getSalePrice(),
                        x.getProduct().getComparePrice(),
                        url);
            }).collect(Collectors.toList());
        }else if (principal instanceof String && principal.equals("anonymousUser")) {
            List<Product> productCollect = cardProductRequestDto.stream().map(x -> productRepository.findById(x.getProductId()).orElseThrow(()-> new NotFoundException("Product "+ ExceptionMessage.NOT_FOUND.getMessage()))).toList();
            return productCollect.stream().map(x-> {
                String coverImageUrl = "";
                if (x.getCoverImage() != null) {
                    coverImageUrl = x.getCoverImage().getUrl();
                }
                return new CardResponseDetails(x.getId(),
                        x.getProductName(),
                        x.getSalePrice(),
                        x.getComparePrice(),
                        coverImageUrl);
            }).toList();
        }else
            throw new BadRequestException("Geçersiz Kullanıcı");
    }
}
