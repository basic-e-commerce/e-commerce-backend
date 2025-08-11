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
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.exception.UnAuthorizedException;
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
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
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
            throw new UnAuthorizedException("Lütfen giriş yapınız");
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

            if(coupon == null)
                throw new NotFoundException("Kupon bulunamadı!");

            System.out.println("kupon kodu: "+coupon.getCode());

            CustomerCoupon customerCoupon = customerCouponService.findCouponAndCustomer(coupon,customer);
            if (customerCoupon != null) {
                if (customerCoupon.getUsed())
                    throw new BadRequestException("Bu Kupon kullanılmıştır!");
            }

            isCouponValidationNew(coupon,customer.getCard().getItems(), customer);
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

    private void isCouponValidationNew(Coupon coupon,List<CardItem> items,Customer customer) {
        if (!coupon.getActive()){
            System.out.println("Kupon aktif değildir");
            throw new BadRequestException("Kupon aktif değildir");
        }

        if (coupon.getTimesUsed() >= coupon.getTotalUsageLimit()){
            throw new BadRequestException("Kuponun Kullanım Limiti Dolmuştur");
        }

        Instant now = Instant.now();

        if (coupon.getCouponStartDate() != null && now.isBefore(coupon.getCouponStartDate())) {
            throw new BadRequestException("Kupon henüz geçerli değildir!");
        }

        if (coupon.getCouponEndDate() != null && now.isAfter(coupon.getCouponEndDate())) {
            throw new BadRequestException("Kuponun geçerlilik süresi sona ermiştir!");
        }

        if (coupon.getProductAssigned()){
            List<Product> couponProducts = new ArrayList<>(coupon.getProducts());
            boolean flag = false;
            for (CardItem item : items) {
                for (Product couponProduct : couponProducts) {
                    if (couponProduct.getId() == item.getProduct().getId()) {
                        flag = true;
                    }
                }
            }
            if (!flag)
                throw new BadRequestException("Sepette kupon için geçerli ürün bulunamamaktadır!");
        }

        CustomerCoupon customerCoupon = customerCouponService.findCouponAndCustomer(coupon,customer);
        if (customerCoupon != null) {
            if (customerCoupon.getUsed())
                throw new ResourceAlreadyExistException("Bu kupon kullanılmıştır!");
        }

        BigDecimal orderPrice = BigDecimal.valueOf(0);

        for (CardItem orderItem : items) {
            BigDecimal cardItemPrice = orderItem.getProduct().getComparePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            orderPrice = orderPrice.add(cardItemPrice);
        }

        System.out.println("ORderprice:::" + orderPrice);
        // orderPrice hesaplandıktan sonra kontroller
        // Minimum sepet tutarı kontrolü
        if (coupon.getMinOrderAmountLimit() != null &&
                orderPrice.compareTo(coupon.getMinOrderAmountLimit()) < 0) {
            throw new BadRequestException("Sepet minimum tutara sahip değildir!");
        }

        // Maksimum sepet tutarı kontrolü
        if (coupon.getMaxOrderAmountLimit() != null &&
                orderPrice.compareTo(coupon.getMaxOrderAmountLimit()) > 0) {
            throw new BadRequestException("Sepet maksimum tutardan yüksektir!");
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
