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
    private final CardBuilder cardBuilder;
    private final ProductService productService;
    private final CustomerCouponService customerCouponService;
    private final CouponService couponService;

    public CardService(CardRepository cardRepository, CardBuilder cardBuilder, ProductService productService, CustomerCouponService customerCouponService, CouponService couponService) {
        this.cardRepository = cardRepository;
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
        }

        // 3. Müşterinin sepetini al
        Card card = customer.getCard();

        for (CardItemCreateDto cardItemCreateDto : cardCreateDto.getCardItems()) {
            Product product = productService.findProductById(cardItemCreateDto.productId());
            int productQuantity = product.getQuantity();

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
                } else {
                    // 7. Miktarı güncelle
                    existingCardItem.setQuantity(totalQuantity);
                    cardRepository.save(card);
                }
            } else {
                if (cardItemCreateDto.quantity() <= 0)
                    throw new BadRequestException("0 ve altında adet olamaz");

                if (cardItemCreateDto.quantity() > productQuantity)
                    throw new BadRequestException("Yetersiz Ürün Stoğu: "+cardItemCreateDto.productId());

                CardItem cardItem = new CardItem(
                        product,
                        cardItemCreateDto.quantity()
                );
                card.getItems().add(cardItem);
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
            Coupon coupon = couponService.findByCodeNull(code);
            CustomerCoupon customerCoupon = customerCouponService.findCouponAndCustomer(coupon,customer);

            if (customerCoupon != null){
                if (customerCoupon.getUsed()){
                    throw new NotFoundException("Bu kupon Kullanılmıştır!");
                }
            }

            isCouponValidation(coupon,customer);
            customer.getCard().setCoupon(coupon);
            cardRepository.save(customer.getCard());
            return "Kupon Eklendi!";
        }else
            throw new BadRequestException("Lütfen giriş yapınız!");
    }

    public String removeCoupon() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Customer customer) {
            customer.getCard().setCoupon(null);
            cardRepository.save(customer.getCard());
            return "Kupon Kaldırıldı!";
        }else
            throw new BadRequestException("Lütfen giriş yapınız!");
    }
    public void isCouponValidation(Coupon coupon,Customer customer) {
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

        System.out.println("totalprice: "+ totalPrice(customer.getCard()));
        if (totalPrice(customer.getCard()).compareTo(coupon.getMinOrderAmountLimit()) < 0) {
            throw new BadRequestException("Sipariş tutarı kuponun minimum limiti olan " + coupon.getMinOrderAmountLimit() + " TL'den küçük.");
        }

        if (coupon.getCustomerAssigned()){
            if (!coupon.getCustomers().contains(customer))
                throw new BadRequestException("Bu Kupon Kullanılamamaktadır!");
        }

    }

    public BigDecimal totalPrice(Card card) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        //Set<OrderItem> newOrderItems = new HashSet<>();
        for (CardItem cardItem: card.getItems()) {
            totalPrice = totalPrice.add(cardItem.getProduct().getComparePrice().multiply(BigDecimal.valueOf(cardItem.getQuantity())));
        }
        return totalPrice;
    }
}
