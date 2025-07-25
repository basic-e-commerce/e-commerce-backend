package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.dto.product.card.*;
import com.example.ecommercebackend.dto.product.coupon.CouponCustomerResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;
import com.example.ecommercebackend.dto.product.products.ProductQuantityDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.card.CardItemRepository;
import com.example.ecommercebackend.repository.product.products.ProductRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.merchant.MerchantService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardItemService {
    private final CardItemRepository cardItemRepository;
    private final ProductRepository productRepository;
    private final MerchantService merchantService;
    private final CustomerRepository customerRepository;


    public CardItemService(CardItemRepository cardItemRepository, ProductRepository productRepository, MerchantService merchantService, CustomerRepository customerRepository) {
        this.cardItemRepository = cardItemRepository;
        this.productRepository = productRepository;
        this.merchantService = merchantService;
        this.customerRepository = customerRepository;
    }


    public CardResponseDetail getDetails(List<CardProductRequestDto> cardProductRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Customer customer1) {
            Customer customer = customerRepository.findById(customer1.getId()).get();

            List<CardItem> cardItems = customer.getCard().getItems();
            Coupon coupon = customer.getCard().getCoupon();

            if (coupon != null) {
                coupon = isCouponValidationNew(coupon,customer.getCard().getItems());
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

            List<CardResponseDetails> cardResponseDetails = new ArrayList<>();
            for (CardItem cardItem : cardItems) {
                CardResponseDetails cardResponseDetail = new CardResponseDetails(
                        cardItem.getProduct().getId(),
                        cardItem.getProduct().getProductName(),
                        cardItem.getProduct().getProductLinkName(),
                        cardItem.getProduct().getSalePrice(),
                        cardItem.getProduct().getComparePrice(),
                        cardItem.getProduct().getCoverImage().getUrl(),
                        cardItem.getQuantity()
                );
                cardResponseDetails.add(cardResponseDetail);
            }


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

    private Coupon isCouponValidationNew(Coupon coupon,List<CardItem> items) {
        if (!coupon.getActive()){
            System.out.println("Kupon aktif değildir");
            return null;
        }

        if (coupon.getTimesUsed() >= coupon.getTotalUsageLimit()){
            System.out.println("Kuponun Kullanım Limiti Dolmuştur");
            return null;
        }

        Instant now = Instant.now();

        if (coupon.getCouponStartDate() != null && now.isBefore(coupon.getCouponStartDate())) {
            System.out.println("Kupon henüz geçerli değildir!");
            return null;
        }

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

        return coupon;
    }

    private BigDecimal totalWithoutShippingCost(BigDecimal totalWithOutTax, BigDecimal totalTax, BigDecimal discount) {
        return totalWithOutTax.add(totalTax).subtract(discount);
    }
//
//    public CardResponseDetail getDetails(List<CardProductRequestDto> cardProductRequestDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("principal : "+ authentication.getPrincipal());
//
//        if (authentication.getPrincipal() instanceof Customer customer1) {
//            Customer customer = customerRepository.findById(customer1.getId()).get();
//
//            List<ProductQuantityDto> cardProduct = new ArrayList<>();   // döndürülecek product product ve quantity için liste oluşturuluyor ama kupon varsa bu olacak yoksa olmayacak
//            List<ProductQuantityDto> cardProductNoCoupon = new ArrayList<>();
//            Coupon coupon = customer.getCard().getCoupon();
//            List<CardResponseDetails> productDetails = new ArrayList<>();
//            BigDecimal couponPrice = BigDecimal.ZERO;
//            CouponCustomerResponseDto couponResponseDto = null;
//
//            List<CardItem> items = customer.getCard().getItems();
//            //int itemCount = (int) items.stream().filter(i -> coupon != null && coupon.getProductAssigned() ? coupon.getProducts().contains(i.getProduct()) : true).count();
//
//            int itemCount  = 0;
//            if (coupon != null){
//
//                List<Product> couponProduct = new ArrayList<>(coupon.getProducts());
//
//                if (coupon.getProductAssigned()){
//                    for (CardItem item : items) {
//                        for(Product product : couponProduct){
//                            if (product.getId() == item.getProduct().getId()){
//                                itemCount += item.getQuantity();
//                            }
//                        }
//                    }
//                }else{
//                    for (CardItem item : items) {
//                        itemCount+=item.getQuantity();
//                    }
//                }
//            }
//
//
//
//
//            for (CardItem item : items) {
//                Product product = item.getProduct();
//                String url = (product.getCoverImage() != null) ? product.getCoverImage().getUrl() : "";
//                BigDecimal comparePrice = product.getComparePrice();
//                BigDecimal productCouponPrice = couponPrice;
//
//
//                if (coupon != null) {
//                    isCouponValidation(coupon,customer);
//                    BigDecimal discountValue = coupon.getDiscountValue();
//                    boolean assigned = coupon.getProductAssigned();
//                    boolean isInCoupon = assigned && coupon.getProducts().contains(product);
//
//                    if (discountValue == null)
//                        throw new BadRequestException("İndirim oranı/miktarı eksik");
//
//                    if (coupon.getDiscountType().equals(Coupon.DiscountType.PERCENTAGE)) {
//                        if (!assigned || isInCoupon) {
//                            BigDecimal discountAmount = comparePrice.multiply(discountValue)
//                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//                            productCouponPrice = comparePrice.subtract(discountAmount);
//                            couponPrice = couponPrice.add(discountAmount.multiply(BigDecimal.valueOf(item.getQuantity())));
//
//                        }
//                    } else if (coupon.getDiscountType().equals(Coupon.DiscountType.FIXEDAMOUNT)) {
//                        if (!assigned || isInCoupon) {
//
//                            BigDecimal perItemDiscount = discountValue
//                                    .divide(BigDecimal.valueOf(itemCount), 2, RoundingMode.HALF_UP);
//                            productCouponPrice = comparePrice.subtract(perItemDiscount);
//                            couponPrice = couponPrice.add(perItemDiscount);
//                        }
//                    }
//                }
//
//                // Listeye ekle
//                cardProduct.add(new ProductQuantityDto(productCouponPrice, product.getTaxRate(), item.getQuantity()));
//                cardProductNoCoupon.add(new ProductQuantityDto(comparePrice, product.getTaxRate(), item.getQuantity()));
//                productDetails.add(new CardResponseDetails(
//                        product.getId(),
//                        product.getProductName(),
//                        product.getProductLinkName(),
//                        product.getSalePrice(),
//                        product.getComparePrice(),
//                        productCouponPrice,
//                        url,
//                        item.getQuantity()
//                ));
//            }
//
//            if (coupon != null) {
//                couponResponseDto = new CouponCustomerResponseDto(
//                        coupon.getCode(),
//                        coupon.getDescription(),
//                        coupon.getCouponStartDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
//                        coupon.getCouponEndDate().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime(),
//                        coupon.getActive()
//                );
//            }
//
//            // Fiyat hesaplamaları
//            BigDecimal totalWithOutTax = getTotalWithOutTax(cardProduct);
//            BigDecimal totalTax = getTotalTaxAmount(cardProduct);
//            BigDecimal shippingCost = BigDecimal.ZERO;
//            float shippingCostRate = getShippingCostRate(getTotalAmountWithOutShippingCost(cardProduct));
//
//            if (getTotalAmountWithOutShippingCost(cardProduct).compareTo(getMerchant().getMinOrderAmount()) < 0) {
//                shippingCost = getMerchant().getShippingFee();
//            }
//
//            BigDecimal totalPrice = getTotalAmountWithShippingCost(cardProduct, shippingCost);
//            BigDecimal totalNoCouponPrice = getTotalAmountWithShippingCost(cardProductNoCoupon, shippingCost);
//
//            return new CardResponseDetail(
//                    totalWithOutTax,
//                    totalTax,
//                    shippingCost,
//                    totalNoCouponPrice,
//                    totalPrice,
//                    couponPrice,
//                    shippingCostRate,
//                    productDetails,
//                    couponResponseDto
//            );
//        }else if (authentication.getPrincipal().equals("anonymousUser")) {
//            List<ProductQuantityDto> productCollect = cardProductRequestDto.stream().map(x -> {
//                Product product =productRepository.findById(x.getProductId()).orElseThrow(()-> new NotFoundException("Product "+ ExceptionMessage.NOT_FOUND.getMessage()));
//                int quantity = x.getQuantity();
//                return new ProductQuantityDto(product.getComparePrice(),product.getTaxRate(),quantity);
//            }).toList();
//
//            List<CardResponseDetails> productDetails = cardProductRequestDto.stream().map(x -> {
//                Product product = productRepository.findById(x.getProductId())
//                        .orElseThrow(() -> new NotFoundException("Product not found"));
//                String coverImageUrl = "";
//                if (product.getCoverImage() != null) {
//                    coverImageUrl = product.getCoverImage().getUrl();
//                }
//                return new CardResponseDetails(product.getId(),
//                        product.getProductName(),
//                        product.getProductLinkName(),
//                        product.getSalePrice(),
//                        product.getComparePrice(),
//                        product.getComparePrice(),
//                        coverImageUrl,
//                        x.getQuantity());
//            }).toList();
//
//            BigDecimal totalWithOutTax = getTotalWithOutTax(productCollect);
//            BigDecimal totalTax = getTotalTaxAmount(productCollect);
//            BigDecimal shippingCost = BigDecimal.ZERO;
//            float shippingCostRate = getShippingCostRate(getTotalAmountWithOutShippingCost(productCollect));
//            if (getTotalAmountWithOutShippingCost(productCollect).compareTo(getMerchant().getMinOrderAmount()) < 0) {
//                shippingCost = getMerchant().getShippingFee();
//            }
//            BigDecimal totalPrice = getTotalAmountWithShippingCost(productCollect,shippingCost);
//
//            return new CardResponseDetail(
//                    totalWithOutTax,
//                    totalTax,
//                    shippingCost,
//                    totalPrice,
//                    totalPrice,
//                    BigDecimal.ZERO,
//                    shippingCostRate,
//                    productDetails,
//                    null
//            );
//
//        }
//
//        throw new BadRequestException("Geçersiz Kullanıcı");
//    }


//    private void isCouponValidation(Coupon coupon,Customer customer) {
//        if (!coupon.getActive())
//            throw new BadRequestException("Kullanılan Kupon Aktif değildir!");
//
//        System.out.println("coupon.getTimesUsed():"+coupon.getTimesUsed());
//        System.out.println("coupon.getTotalUsageLimit(): "+coupon.getTotalUsageLimit());
//        if (coupon.getTimesUsed() >= coupon.getTotalUsageLimit()){
//            customer.getCard().setCoupon(null);
//            customerRepository.save(customer);
//            throw new BadRequestException("Kuponun Kullanım Limiti Dolmuştur");
//        }
//
//
//        Instant now = Instant.now();
//
//        if (coupon.getCouponStartDate() != null && now.isBefore(coupon.getCouponStartDate())) {
//            customer.getCard().setCoupon(null);
//            customerRepository.save(customer);
//            throw new BadRequestException("Kupon henüz geçerli değildir!");
//        }
//
//        if (coupon.getCouponEndDate() != null && now.isAfter(coupon.getCouponEndDate())) {
//            customer.getCard().setCoupon(null);
//            customerRepository.save(customer);
//            throw new BadRequestException("Kuponun geçerlilik süresi sona ermiştir!");
//        }
//
//    }


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
