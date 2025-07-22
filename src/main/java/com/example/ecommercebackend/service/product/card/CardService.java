package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.builder.product.card.CardBuilder;
import com.example.ecommercebackend.dto.product.card.*;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.card.CardRepository;
import com.example.ecommercebackend.service.product.products.CouponService;
import com.example.ecommercebackend.service.product.products.CustomerCouponService;
import com.example.ecommercebackend.service.product.products.ProductService;
import com.example.ecommercebackend.service.user.CustomerService;
import org.hibernate.annotations.NotFound;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardItemService cardItemService;
    private final CardBuilder cardBuilder;
    private final ProductService productService;
    private final CustomerCouponService customerCouponService;
    private final CouponService couponService;

    public CardService(CardRepository cardRepository, CardItemService cardItemService, CardBuilder cardBuilder, ProductService productService, CustomerCouponService customerCouponService, CouponService couponService) {
        this.cardRepository = cardRepository;
        this.cardItemService = cardItemService;
        this.cardBuilder = cardBuilder;
        this.productService = productService;
        this.customerCouponService = customerCouponService;
        this.couponService = couponService;
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

    public String addCoupon(String code) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer) {
            Coupon coupon = couponService.findByCode(code);
            CustomerCoupon customerCoupon = customerCouponService.findCouponAndCustomer(coupon,customer);

            if (customerCoupon == null && !coupon.getPublic())
                throw new NotFoundException("Tanımlanabilecek bir kupon bulunamadı!");

            if (customerCoupon == null && coupon.getPublic()) {
                CustomerCoupon newCustomerCoupon = new CustomerCoupon(customer,coupon,Instant.now());
                customerCoupon = customerCouponService.save(newCustomerCoupon);
            }
            isCouponValidation(coupon,customer.getCard());
            customer.getCard().setCustomerCoupon(customerCoupon);
            cardRepository.save(customer.getCard());
            return "Kupon Eklendi!";
        }else
            throw new BadRequestException("Lütfen giriş yapınız!");
    }

    public String removeCoupon() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer) {
            customer.getCard().setCustomerCoupon(null);
            cardRepository.save(customer.getCard());
            return "Kupon Kaldırıldı!";
        }else
            throw new BadRequestException("Lütfen giriş yapınız!");
    }
    public void isCouponValidation(Coupon coupon,Card card) {
        if (!coupon.getActive())
            throw new BadRequestException("Kullanılan Kupon Aktif değildir!");

        System.out.println("couponcoupon.getTimesUsed():"+coupon.getTimesUsed());
        System.out.println("coupon.getTotalUsageLimit(): "+coupon.getTotalUsageLimit());
        if (coupon.getTimesUsed() >= coupon.getTotalUsageLimit())
            throw new BadRequestException("Kuponun Kullanım Limiti Dolmuştur");

        Instant now = Instant.now();

        if (coupon.getCouponStartDate() != null && now.isBefore(coupon.getCouponStartDate())) {
            throw new BadRequestException("Kupon henüz geçerli değildir!");
        }

        if (coupon.getCouponEndDate() != null && now.isAfter(coupon.getCouponEndDate())) {
            throw new BadRequestException("Kuponun geçerlilik süresi sona ermiştir!");
        }

        if (totalPrice(card).compareTo(coupon.getMinOrderAmountLimit()) < 0) {
            throw new IllegalArgumentException("Sipariş tutarı kuponun minimum limiti olan " + coupon.getMinOrderAmountLimit() + " TL'den küçük.");
        }



    }

    public BigDecimal totalPrice(Card card) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        //Set<OrderItem> newOrderItems = new HashSet<>();
        for (CardItem cardItem: card.getItems()) {
            totalPrice = totalPrice.add(cardItem.getProduct().getComparePrice());
        }
        return totalPrice;
    }
}
