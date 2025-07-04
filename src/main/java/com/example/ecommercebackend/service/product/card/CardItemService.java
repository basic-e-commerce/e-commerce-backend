package com.example.ecommercebackend.service.product.card;

import com.example.ecommercebackend.dto.product.card.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
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


    public CardResponseDetail getDetails(List<CardProductRequestDto> cardProductRequestDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);

        if (principal instanceof Customer customer1) {
            Customer customer = customerRepository.findById(customer1.getId()).get();
            List<ProductQuantityDto> cardProduct = new ArrayList<>();

            CustomerCoupon customerCoupon = customer.getCard().getCustomerCoupon();
            List<CardResponseDetails> productDetails = new ArrayList<>();

            if (customerCoupon != null) {
                isCouponValidation(customerCoupon);
                if (customerCoupon.getCoupon().getDiscountType().equals(Coupon.DiscountType.PERCENTAGE)){
                    productDetails = customer.getCard().getItems().stream().map(x -> {
                        String url = "";
                        if (x.getProduct().getCoverImage() != null) {
                            url = x.getProduct().getCoverImage().getUrl();
                        }
                        cardProduct.add(new ProductQuantityDto(x.getProduct(), x.getQuantity()));

                        boolean isProductInCoupon = customerCoupon.getCoupon().getProducts().stream()
                                .anyMatch(product -> product.equals(x.getProduct()));

                        BigDecimal comparePrice = x.getProduct().getComparePrice();
                        if (isProductInCoupon)
                            comparePrice = comparePrice.subtract(comparePrice.multiply(customerCoupon.getCoupon().getDiscountValue()).divide(BigDecimal.valueOf(100)));

                        return new CardResponseDetails(x.getProduct().getId(),
                                x.getProduct().getProductName(),
                                x.getProduct().getProductLinkName(),
                                x.getProduct().getSalePrice(),
                                comparePrice,
                                url,
                                x.getQuantity());
                    }).toList();
                }else if (customerCoupon.getCoupon().getDiscountType().equals(Coupon.DiscountType.FIXEDAMOUNT)){
                    productDetails = customer.getCard().getItems().stream().map(x -> {
                        String url = "";
                        if (x.getProduct().getCoverImage() != null) {
                            url = x.getProduct().getCoverImage().getUrl();
                        }
                        cardProduct.add(new ProductQuantityDto(x.getProduct(), x.getQuantity()));

                        boolean isProductInCoupon = customerCoupon.getCoupon().getProducts().stream()
                                .anyMatch(product -> product.equals(x.getProduct()));

                        BigDecimal comparePrice = x.getProduct().getComparePrice();
                        if (isProductInCoupon)
                            comparePrice = comparePrice.subtract(customerCoupon.getCoupon().getDiscountValue());

                        return new CardResponseDetails(x.getProduct().getId(),
                                x.getProduct().getProductName(),
                                x.getProduct().getProductLinkName(),
                                x.getProduct().getSalePrice(),
                                comparePrice,
                                url,
                                x.getQuantity());
                    }).toList();

                }else {
                    productDetails = customer.getCard().getItems().stream().map(x -> {
                        String url = "";
                        if (x.getProduct().getCoverImage() != null) {
                            url = x.getProduct().getCoverImage().getUrl();
                        }
                        cardProduct.add(new ProductQuantityDto(x.getProduct(), x.getQuantity()));

                        return new CardResponseDetails(x.getProduct().getId(),
                                x.getProduct().getProductName(),
                                x.getProduct().getProductLinkName(),
                                x.getProduct().getSalePrice(),
                                x.getProduct().getComparePrice(),
                                url,
                                x.getQuantity());
                    }).toList();
                }


            }else {
                productDetails = customer.getCard().getItems().stream().map(x -> {
                    String url = "";
                    if (x.getProduct().getCoverImage() != null) {
                        url = x.getProduct().getCoverImage().getUrl();
                    }
                    cardProduct.add(new ProductQuantityDto(x.getProduct(), x.getQuantity()));

                    return new CardResponseDetails(x.getProduct().getId(),
                            x.getProduct().getProductName(),
                            x.getProduct().getProductLinkName(),
                            x.getProduct().getSalePrice(),
                            x.getProduct().getComparePrice(),
                            url,
                            x.getQuantity());
                }).toList();
            }

            BigDecimal totalWithOutTax = getTotalWithOutTax(cardProduct);
            BigDecimal totalTax = getTotalTaxAmount(cardProduct);

            BigDecimal shippingCost = BigDecimal.ZERO;
            float shippingCostRate = getShippingCostRate(getTotalAmountWithOutShippingCost(cardProduct));



            if (getTotalAmountWithOutShippingCost(cardProduct).compareTo(getMerchant().getMinOrderAmount()) < 0) {
                shippingCost = getMerchant().getShippingFee();
            }
            BigDecimal totalPrice = getTotalAmountWithShippingCost(cardProduct,shippingCost);


            return new CardResponseDetail(
                    totalWithOutTax,
                    totalTax,
                    shippingCost,
                    totalPrice,
                    shippingCostRate,
                    productDetails
                    );

        }else if (principal instanceof String && principal.equals("anonymousUser")) {

            List<ProductQuantityDto> productCollect = cardProductRequestDto.stream().map(x -> {
                Product product =productRepository.findById(x.getProductId()).orElseThrow(()-> new NotFoundException("Product "+ ExceptionMessage.NOT_FOUND.getMessage()));
                int quantity = x.getQuantity();
                return new ProductQuantityDto(product,quantity);
            }).toList();

            List<CardResponseDetails> productDetails = productCollect.stream().map(x -> {
                String coverImageUrl = "";
                if (x.getProduct().getCoverImage() != null) {
                    coverImageUrl = x.getProduct().getCoverImage().getUrl();
                }
                return new CardResponseDetails(x.getProduct().getId(),
                        x.getProduct().getProductName(),
                        x.getProduct().getProductLinkName(),
                        x.getProduct().getSalePrice(),
                        x.getProduct().getComparePrice(),
                        coverImageUrl,
                        x.getQuantity());
            }).toList();

            BigDecimal totalWithOutTax = getTotalWithOutTax(productCollect);
            BigDecimal totalTax = getTotalTaxAmount(productCollect);

            BigDecimal shippingCost = BigDecimal.ZERO;
            float shippingCostRate = getShippingCostRate(getTotalAmountWithOutShippingCost(productCollect));



            if (getTotalAmountWithOutShippingCost(productCollect).compareTo(getMerchant().getMinOrderAmount()) < 0) {
                shippingCost = getMerchant().getShippingFee();
            }
            BigDecimal totalPrice = getTotalAmountWithShippingCost(productCollect,shippingCost);

            return new CardResponseDetail(
                    totalWithOutTax,
                    totalTax,
                    shippingCost,
                    totalPrice,
                    shippingCostRate,
                    productDetails
            );
        }else
            throw new BadRequestException("Geçersiz Kullanıcı");
    }

    private void isCouponValidation(CustomerCoupon customerCoupon) {
        if (!customerCoupon.getCoupon().getActive())
            throw new BadRequestException("Kullanılan Kupon Aktif değildir!");

        System.out.println("customerCoupon.getCoupon().getTimesUsed():"+customerCoupon.getCoupon().getTimesUsed());
        System.out.println("customerCoupon.getCoupon().getTotalUsageLimit(): "+customerCoupon.getCoupon().getTotalUsageLimit());
        if (customerCoupon.getCoupon().getTimesUsed() >= customerCoupon.getCoupon().getTotalUsageLimit())
            throw new BadRequestException("Kuponun Kullanım Limiti Dolmuştur");

        Instant now = Instant.now();

        if (customerCoupon.getCoupon().getCouponStartDate() != null && now.isBefore(customerCoupon.getCoupon().getCouponStartDate())) {
            throw new BadRequestException("Kupon henüz geçerli değildir!");
        }

        if (customerCoupon.getCoupon().getCouponEndDate() != null && now.isAfter(customerCoupon.getCoupon().getCouponEndDate())) {
            throw new BadRequestException("Kuponun geçerlilik süresi sona ermiştir!");
        }
    }


    public BigDecimal getTotalWithOutTax(List<ProductQuantityDto> productQuantityDtos) {
        BigDecimal totalWithOutTax = BigDecimal.ZERO;

        for (ProductQuantityDto pq : productQuantityDtos) {
            Product product = pq.getProduct();
            int quantity = pq.getQuantity();

            BigDecimal comparePrice = product.getComparePrice(); // Vergili fiyat
            BigDecimal taxRate = product.getTaxRate(); // Örn: 18

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
            Product product = pq.getProduct();
            int quantity = pq.getQuantity();

            BigDecimal comparePrice = product.getComparePrice(); // Vergili fiyat
            BigDecimal taxRate = product.getTaxRate(); // Örn: 18

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
            Product product = pq.getProduct();
            int quantity = pq.getQuantity();

            BigDecimal totalForProduct = product.getComparePrice().multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(totalForProduct);
        }

        return totalAmount;
    }


    public BigDecimal getTotalAmountWithShippingCost(List<ProductQuantityDto> productQuantityDtos, BigDecimal shippingCost) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ProductQuantityDto pq : productQuantityDtos) {
            Product product = pq.getProduct();
            int quantity = pq.getQuantity();

            BigDecimal totalForProduct = product.getComparePrice().multiply(BigDecimal.valueOf(quantity));
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
