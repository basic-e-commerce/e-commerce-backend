package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.dto.product.card.*;
import com.example.ecommercebackend.dto.product.coupon.CouponCustomerResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;
import com.example.ecommercebackend.dto.product.products.ProductQuantityDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.card.CardItemRepository;
import com.example.ecommercebackend.repository.product.products.CustomerCouponRepository;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.example.ecommercebackend.service.product.products.CustomerCouponService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardItemService {
    private final CardItemRepository cardItemRepository;
    private final ProductRepository productRepository;
    private final MerchantService merchantService;
    private final CustomerRepository customerRepository;
    private final CustomerCouponService customerCouponService;


    public CardItemService(CardItemRepository cardItemRepository, ProductRepository productRepository, MerchantService merchantService, CustomerRepository customerRepository, CustomerCouponService customerCouponService) {
        this.cardItemRepository = cardItemRepository;
        this.productRepository = productRepository;
        this.merchantService = merchantService;
        this.customerRepository = customerRepository;
        this.customerCouponService = customerCouponService;
    }


    public CardResponseDetail getDetails(List<CardProductRequestDto> cardProductRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Customer customer1) {
            Customer customer = customerRepository.findById(customer1.getId()).get();

            List<CardItem> cardItems = customer.getCard().getItems();
            Coupon coupon = customer.getCard().getCoupon();

            if (coupon != null) {
                coupon = isCouponValidationNew(coupon,customer.getCard().getItems(),customer);
            }

            if (coupon == null) {
                customer.getCard().setCoupon(null);
                customerRepository.save(customer);
            }

            CouponCustomerResponseDto couponCustomerResponseDto = null;
            if (coupon != null) {
                couponCustomerResponseDto = new CouponCustomerResponseDto(
                        coupon.getCode(),
                        coupon.getDiscountType().name(),
                        coupon.getDiscountValue().setScale(2, RoundingMode.HALF_UP).toPlainString(),
                        coupon.getDescription(),
                        coupon.getCouponStartDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
                        coupon.getCouponEndDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
                        coupon.getActive()
                );
            }
            List<ProductQuantityDto> productQuantityDtos = new ArrayList<>();

            for (CardItem cardItem : cardItems) {
                ProductQuantityDto productQuantityDto = new ProductQuantityDto(
                        cardItem.getProduct().getId(),
                        cardItem.getProduct().getComparePrice(),
                        cardItem.getProduct().getTaxRate(),
                        cardItem.getQuantity()
                );
                productQuantityDtos.add(productQuantityDto);
            }

            List<CardResponseDetails> cardResponseDetails = cardItems.stream()
                    .sorted(Comparator.comparing(cardItem -> cardItem.getProduct().getComparePrice()))
                    .map(cardItem -> new CardResponseDetails(
                            cardItem.getProduct().getId(),
                            cardItem.getProduct().getProductName(),
                            cardItem.getProduct().getProductLinkName(),
                            cardItem.getProduct().getSalePrice(),
                            cardItem.getProduct().getComparePrice(),
                            cardItem.getProduct().getCoverImage().getUrl(),
                            cardItem.getQuantity()
                    ))
                    .collect(Collectors.toList());


            BigDecimal totalWithOutTax = getTotalWithOutTax(productQuantityDtos);
            BigDecimal totalTax = getTotalTaxAmount(productQuantityDtos);
            BigDecimal discount = BigDecimal.ZERO;

            if (coupon != null) {
                discount = getCouponDiscountAmount(coupon,cardResponseDetails);
            }

            BigDecimal totalWithoutShipping = totalWithoutShippingCost(totalWithOutTax,totalTax,discount);
            BigDecimal shippingCost = BigDecimal.ZERO;
            float shippingCostRate = getShippingCostRate(getTotalAmountWithOutShippingCost(productQuantityDtos));

            if (totalWithoutShipping.compareTo(getMerchant().getMinOrderAmount()) < 0) {
                shippingCost = getMerchant().getShippingFee();
            }
            BigDecimal totalPrice = totalWithoutShipping.add(shippingCost);

            return new CardResponseDetail(
                    totalWithOutTax,
                    totalTax,
                    shippingCost,
                    totalPrice,
                    discount,
                    shippingCostRate,
                    cardResponseDetails,
                    couponCustomerResponseDto
            );

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            List<ProductQuantityDto> productCollect = cardProductRequestDto.stream().map(x -> {
                Product product =productRepository.findById(x.getProductId()).orElseThrow(()-> new NotFoundException("Product "+ ExceptionMessage.NOT_FOUND.getMessage()));
                int quantity = x.getQuantity();
                return new ProductQuantityDto(
                        product.getId(),
                        product.getComparePrice(),
                        product.getTaxRate(),
                        quantity);
            }).toList();

            List<CardResponseDetails> productDetails = cardProductRequestDto.stream().map(x -> {
                Product product = productRepository.findById(x.getProductId())
                        .orElseThrow(() -> new NotFoundException("Ürün bulunamadı!"));
                String coverImageUrl = "";
                if (product.getCoverImage() != null) {
                    coverImageUrl = product.getCoverImage().getUrl();
                }
                return new CardResponseDetails(product.getId(),
                        product.getProductName(),
                        product.getProductLinkName(),
                        product.getSalePrice(),
                        product.getComparePrice(),
                        coverImageUrl,
                        x.getQuantity());
            }).toList();

            BigDecimal totalWithOutTax = getTotalWithOutTax(productCollect);
            BigDecimal totalTax = getTotalTaxAmount(productCollect);
            BigDecimal discount = BigDecimal.ZERO;
            BigDecimal totalWithoutShipping = totalWithoutShippingCost(totalWithOutTax,totalTax,discount);

            BigDecimal shippingCost = BigDecimal.ZERO;
            float shippingCostRate = getShippingCostRate(getTotalAmountWithOutShippingCost(productCollect));

            if (totalWithoutShipping.compareTo(getMerchant().getMinOrderAmount()) < 0) {
                shippingCost = getMerchant().getShippingFee();
            }
            BigDecimal totalPrice = totalWithoutShipping.add(shippingCost);

            return new CardResponseDetail(
                    totalWithOutTax,
                    totalTax,
                    shippingCost,
                    totalPrice,
                    discount,
                    shippingCostRate,
                    productDetails,
                    null
            );

        }else
            throw new BadRequestException("Geçersiz Kullanıcı");

    }

    private BigDecimal getCouponDiscountAmount(Coupon coupon, List<CardResponseDetails> cardResponseDetails) {

        if (coupon.getProductAssigned()){
            BigDecimal totalPrice = BigDecimal.ZERO;

            List<CardResponseDetails> discountProducts = new ArrayList<>();

            for (CardResponseDetails productQuantityDtos : cardResponseDetails) {
                for (Product product: coupon.getProducts()) {
                    if (productQuantityDtos.getId() == product.getId()) {
                        discountProducts.add(productQuantityDtos);
                    }
                }
            }

            for (CardResponseDetails cardResponseDetailsCouponProduct: discountProducts) {
                totalPrice = totalPrice.add(cardResponseDetailsCouponProduct.getComparePrice().multiply(BigDecimal.valueOf(cardResponseDetailsCouponProduct.getQuantity())));
            }

            if (coupon.getDiscountType().equals(Coupon.DiscountType.FIXEDAMOUNT)){
                BigDecimal discountAmount = totalPrice.subtract(coupon.getDiscountValue());
                if (discountAmount.compareTo(BigDecimal.ZERO) < 0) { // discountAmount 0'dan küçüktür
                    return totalPrice;
                }else
                    return coupon.getDiscountValue();

            } else if (coupon.getDiscountType().equals(Coupon.DiscountType.PERCENTAGE)) {

                BigDecimal percentage = coupon.getDiscountValue(); // Örn: %10 için 10
                return totalPrice.multiply(percentage).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            }else
                throw new BadRequestException("Geçersiz indirim tipi");



        }else{
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CardResponseDetails cardResponseDetailsCouponProduct: cardResponseDetails) {
                totalPrice = totalPrice.add(cardResponseDetailsCouponProduct.getComparePrice().multiply(BigDecimal.valueOf(cardResponseDetailsCouponProduct.getQuantity())));
            }

            if (coupon.getDiscountType().equals(Coupon.DiscountType.FIXEDAMOUNT)){
                BigDecimal discountAmount = totalPrice.subtract(coupon.getDiscountValue());
                if (discountAmount.compareTo(BigDecimal.ZERO) < 0) { // discountAmount 0'dan küçüktür
                    return totalPrice;
                }else
                    return coupon.getDiscountValue();

            } else if (coupon.getDiscountType().equals(Coupon.DiscountType.PERCENTAGE)) {

                BigDecimal percentage = coupon.getDiscountValue(); // Örn: %10 için 10
                return totalPrice.multiply(percentage).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            }else
                throw new BadRequestException("Geçersiz indirim tipi");

        }
    }

    private Coupon isCouponValidationNew(Coupon coupon,List<CardItem> items,Customer customer) {
        if (!coupon.getActive()){
            System.out.println("Kupon aktif değildir");
            return null;
        }
        System.out.println(1);

        if (coupon.getTimesUsed() >= coupon.getTotalUsageLimit()){
            System.out.println("Kuponun Kullanım Limiti Dolmuştur");
            return null;
        }
        System.out.println(2);


        Instant now = Instant.now();

        if (coupon.getCouponStartDate() != null && now.isBefore(coupon.getCouponStartDate())) {
            System.out.println("Kupon henüz geçerli değildir!");
            return null;
        }
        System.out.println(3);

        if (coupon.getCouponEndDate() != null && now.isAfter(coupon.getCouponEndDate())) {
            System.out.println("Kuponun geçerlilik süresi sona ermiştir!");
            return null;
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
                return null;
        }
        CustomerCoupon customerCoupon = customerCouponService.findCouponAndCustomer(coupon,customer);
        if (customerCoupon != null) {
            if (customerCoupon.getUsedQuantity() >= coupon.getUserTimeUsed())
                return null;
        }

        BigDecimal orderPrice = BigDecimal.valueOf(0);

        for (CardItem orderItem : items) {
            BigDecimal discountPrice = orderItem.getProduct().getComparePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            orderPrice = orderPrice.add(discountPrice);
        }

        // orderPrice hesaplandıktan sonra kontroller
        // Minimum sepet tutarı kontrolü
        if (coupon.getMinOrderAmountLimit() != null &&
                orderPrice.compareTo(coupon.getMinOrderAmountLimit()) < 0) {
            return null;
        }

        // Maksimum sepet tutarı kontrolü
        if (coupon.getMaxOrderAmountLimit() != null &&
                orderPrice.compareTo(coupon.getMaxOrderAmountLimit()) > 0) {
            return null;
        }


        return coupon;
    }

    private BigDecimal totalWithoutShippingCost(BigDecimal totalWithOutTax, BigDecimal totalTax, BigDecimal discount) {
        return totalWithOutTax.add(totalTax).subtract(discount);
    }


    public BigDecimal getTotalWithOutTax(List<ProductQuantityDto> productQuantityDtos) {
        BigDecimal totalWithOutTax = BigDecimal.ZERO;

        for (ProductQuantityDto pq : productQuantityDtos) {
            int quantity = pq.getQuantity();

            BigDecimal comparePrice = pq.getComparePrice(); // Vergili fiyat
            BigDecimal taxRate = pq.getTaxRate(); // Örn: 18

            // Vergisiz fiyat = Vergili fiyat / (1 + vergiOranı / 100)
            BigDecimal divisor = BigDecimal.ONE.add(taxRate.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
            BigDecimal priceExcludingTax = comparePrice.divide(divisor, 2, RoundingMode.HALF_UP);

            // Vergisiz toplam = vergisiz fiyat * adet
            BigDecimal totalForProduct = priceExcludingTax.multiply(BigDecimal.valueOf(quantity));
            totalWithOutTax = totalWithOutTax.add(totalForProduct);
        }

        return totalWithOutTax;
    }


    public BigDecimal getTotalTaxAmount(List<ProductQuantityDto> productQuantityDtos) {
        BigDecimal totalTax = BigDecimal.ZERO;

        for (ProductQuantityDto pq : productQuantityDtos) {

            int quantity = pq.getQuantity();

            BigDecimal comparePrice = pq.getComparePrice(); // Vergili fiyat
            BigDecimal taxRate = pq.getTaxRate(); // Örn: 18

            BigDecimal divisor = BigDecimal.ONE.add(taxRate.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
            BigDecimal priceExcludingTax = comparePrice.divide(divisor, 2, RoundingMode.HALF_UP);

            BigDecimal taxAmount = comparePrice.subtract(priceExcludingTax); // Vergi tutarı (tek ürün için)
            BigDecimal totalTaxForProduct = taxAmount.multiply(BigDecimal.valueOf(quantity)); // Toplam vergi

            totalTax = totalTax.add(totalTaxForProduct);
        }

        return totalTax;
    }


    public BigDecimal getTotalAmountWithOutShippingCost(List<ProductQuantityDto> productQuantityDtos) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ProductQuantityDto pq : productQuantityDtos) {
            int quantity = pq.getQuantity();

            BigDecimal totalForProduct = pq.getComparePrice().multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(totalForProduct);
        }
        return totalAmount;
    }


    public BigDecimal getTotalAmountWithShippingCost(List<ProductQuantityDto> productQuantityDtos, BigDecimal shippingCost) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ProductQuantityDto pq : productQuantityDtos) {
            int quantity = pq.getQuantity();

            BigDecimal totalForProduct = pq.getComparePrice().multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(totalForProduct);
        }

        return totalAmount.add(shippingCost);
    }


    private float getShippingCostRate(BigDecimal totalAmountWithOutShippingCost) {
        BigDecimal minOrderAmount = getMerchant().getMinOrderAmount();

        if (minOrderAmount.compareTo(BigDecimal.ZERO) == 0) {
            // Sıfıra bölme hatasını önlemek için
            return 0f;
        }

        BigDecimal rate = totalAmountWithOutShippingCost
                .divide(minOrderAmount, 4, RoundingMode.HALF_UP) // oranı hesapla
                .multiply(BigDecimal.valueOf(100));             // yüzdeye çevir

        return rate.floatValue(); // float olarak döndür
    }


    public List<CardItem> findAll() {
        return cardItemRepository.findAll();
    }

    public Merchant getMerchant() {
        return merchantService.getMerchant();
    }


}
