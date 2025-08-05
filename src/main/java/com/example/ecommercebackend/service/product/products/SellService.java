package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.product.sell.SellBuilder;
import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.payment.response.OrderItemTansactionId;
import com.example.ecommercebackend.dto.product.card.ProductCardItemDto;
import com.example.ecommercebackend.dto.product.products.LowStockNotification;
import com.example.ecommercebackend.dto.product.products.ProductAdminDetailDto;
import com.example.ecommercebackend.dto.product.products.ProductSmallDto;
import com.example.ecommercebackend.dto.product.sell.*;
import com.example.ecommercebackend.dto.user.TimeDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.product.products.SellRepository;
import com.example.ecommercebackend.service.payment.PaymentService;
import com.example.ecommercebackend.service.product.card.CardItemService;
import com.example.ecommercebackend.service.product.card.CardService;
import com.example.ecommercebackend.service.product.order.OrderService;
import com.example.ecommercebackend.service.user.CustomerService;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SellService {
    private final CustomerService customerService;
    private final SellRepository sellRepository;
    private final ProductService productService;
    private final SellBuilder sellBuilder;
    private final OrderService orderService;
    private final CardItemService cardItemService;

    public SellService(CustomerService customerService, SellRepository sellRepository, ProductService productService, SellBuilder sellBuilder, OrderService orderService, CardItemService cardItemService) {
        this.customerService = customerService;
        this.sellRepository = sellRepository;
        this.productService = productService;
        this.sellBuilder = sellBuilder;
        this.orderService = orderService;
        this.cardItemService = cardItemService;
    }

    @Transactional
    public Sell save(OrderItem orderItem, OrderItemTansactionId orderItemTansactionId) {
        Product product = orderItem.getProduct();
        System.out.println("sell product: " + product.getProductName());
        System.out.println("sell product quantity: : " + product.getQuantity());

        System.out.println("orderItem quantity: : " + orderItem.getQuantity());
        System.out.println("kalan quantity: " + (product.getQuantity() - orderItem.getQuantity()));

        product.setQuantity(product.getQuantity() - orderItem.getQuantity());
        if (product.getQuantity() <= 0) {
            product.setDisableOutOfStock(true);
        }
        Product save = productService.save(product);

        Sell sell = new Sell(
                save,
                Integer.valueOf(orderItemTansactionId.getOrderItemId()),
                orderItemTansactionId.getPrice(),
                orderItemTansactionId.getPaidPrice(),
                orderItem.getQuantity(),
                orderItemTansactionId.getPaymentTransactionId(),
                orderItemTansactionId.getBasketId(),
                false,
                false
        );
        return sellRepository.save(sell);
    }

    @Transactional
    public Sell saveRefund(OrderItem orderItem, OrderItemTansactionId orderItemTansactionId) {
        Product product = orderItem.getProduct();
        System.out.println("sell product: " + product.getProductName());
        System.out.println("sell product quantity: : " + product.getQuantity());

        System.out.println("orderItem quantity: : " + orderItem.getQuantity());
        System.out.println("kalan quantity: " + (product.getQuantity() - orderItem.getQuantity()));

        product.setQuantity(product.getQuantity() - orderItem.getQuantity());
        if (product.getQuantity() <= 0) {
            product.setDisableOutOfStock(true);
        }
        Product save = productService.save(product);

        Sell sell = new Sell(
                save,
                Integer.valueOf(orderItemTansactionId.getOrderItemId()),
                orderItemTansactionId.getPrice(),
                orderItemTansactionId.getPaidPrice(),
                orderItem.getQuantity(),
                orderItemTansactionId.getPaymentTransactionId(),
                orderItemTansactionId.getBasketId(),
                true,
                false
        );
        return sellRepository.save(sell);
    }

    @Transactional
    public Sell saveCancel(OrderItem orderItem, OrderItemTansactionId orderItemTansactionId) {
        Product product = orderItem.getProduct();
        System.out.println("sell product: " + product.getProductName());
        System.out.println("sell product quantity: : " + product.getQuantity());

        System.out.println("orderItem quantity: : " + orderItem.getQuantity());
        System.out.println("kalan quantity: " + (product.getQuantity() - orderItem.getQuantity()));

        product.setQuantity(product.getQuantity() - orderItem.getQuantity());
        if (product.getQuantity() <= 0) {
            product.setDisableOutOfStock(true);
        }
        Product save = productService.save(product);

        Sell sell = new Sell(
                save,
                Integer.valueOf(orderItemTansactionId.getOrderItemId()),
                orderItemTansactionId.getPrice(),
                orderItemTansactionId.getPaidPrice(),
                orderItem.getQuantity(),
                orderItemTansactionId.getPaymentTransactionId(),
                orderItemTansactionId.getBasketId(),
                false,
                true
        );
        return sellRepository.save(sell);
    }

    public List<ProductSellDto> getSellProducts(ProductSellFilterRequestDto productSellFilterRequestDto, int page, int size) {
        Sort sort = Sort.unsorted();
        if (productSellFilterRequestDto.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(productSellFilterRequestDto.getSortDirection()), productSellFilterRequestDto.getSortBy());
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Sell> specification = getSellProductsFilter(productSellFilterRequestDto);
        return sellRepository.findAll(specification,pageable).stream().map(sellBuilder::sellToProductSellDto).collect(Collectors.toList());
    }

    public ProductDaySellAdmin getOrderProductsDaySell(ProductSellDayFilterRequestDto filter) {
        ZoneId zoneId = ZoneId.of("Europe/Istanbul");

        LocalDate startDate = filter.getStartDate().atZone(zoneId).toLocalDate();
        LocalDate endDate = filter.getEndDate().atZone(zoneId).toLocalDate();
        if (endDate.isAfter(LocalDate.now())) {
            endDate = LocalDate.now();
        }

        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(zoneId).toInstant().minusMillis(1);

        LocalDate minDate = LocalDate.of(2025, 6, 1);
        Instant minInstant = minDate.atStartOfDay(zoneId).toInstant();
        if (startInstant.isBefore(minInstant)) {
            startInstant = minInstant;
        }

        List<Order> orders = orderService.findSuccessOrderBetweenDates(startInstant, endInstant);
        List<Order> refundOrders = orderService.findRefundOrderBetweenDates(startInstant, endInstant);


        Map<String, List<Order>> ordersByPeriod = orders.stream()
                .filter(order -> !order.getCreatedAt().isBefore(minInstant))
                .collect(Collectors.groupingBy(order -> {
                    LocalDate date = order.getCreatedAt().atZone(zoneId).toLocalDate();
                    return getLabelForDate(date, filter.getPeriodType());
                }));

        TemporalAmount step = getStep(filter.getPeriodType());

        LocalDate minInstantToLocalDate = minInstant.atZone(zoneId).toLocalDate();
        if (minInstantToLocalDate.isAfter(startDate)) {
            startDate = minInstantToLocalDate;
        }

        List<ProductDaySell> report = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plus(step)) {
            String label = getLabelForDate(date, filter.getPeriodType());

            List<Order> periodOrders = ordersByPeriod.getOrDefault(label, Collections.emptyList());

            BigDecimal totalAmount = periodOrders.stream()
                    .map(Order::getTotalPrice)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int totalQuantity = periodOrders.stream()
                    .mapToInt(order -> order.getOrderItems().stream()
                            .mapToInt(OrderItem::getQuantity)
                            .sum())
                    .sum();

            report.add(new ProductDaySell(totalAmount, totalQuantity, label));
        }

        int totalQuantity = report.stream()
                .map(ProductDaySell::getQuantity)
                .reduce(0, Integer::sum);

        BigDecimal totalPrice = report.stream()
                .map(ProductDaySell::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalSuccessOrder = orders.size();
        BigDecimal averageOrderAmount = totalSuccessOrder == 0
                ? BigDecimal.ZERO
                : totalPrice.divide(BigDecimal.valueOf(totalSuccessOrder), 2, RoundingMode.HALF_UP);

        // Yeni alanlar: iade edilen toplam tutar ve kargo ücreti
        BigDecimal refundPrice = refundOrders.stream()
                .map(Order::getRefundPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shippingFee = orders.stream()
                .map(Order::getShippingFee)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ProductDaySellAdmin(
                report.stream().sorted(Comparator.comparing(ProductDaySell::getDate)).toList(),
                totalQuantity,
                totalPrice,
                totalSuccessOrder,
                averageOrderAmount,
                refundPrice,
                shippingFee
        );
    }

    private TemporalAmount getStep(String periodType) {
        return switch (periodType.toUpperCase()) {
            case "DAY" -> Period.ofDays(1);
            case "WEEK" -> Period.ofWeeks(1);
            case "MONTH" -> Period.ofMonths(1);
            case "YEAR" -> Period.ofYears(1);
            default -> throw new BadRequestException("Invalid period type.");
        };
    }
    private String getLabelForDate(LocalDate date, String periodType) {
        periodType = periodType.toUpperCase();
        return switch (periodType) {
            case "DAY" -> date.format(DateTimeFormatter.ISO_LOCAL_DATE);
            case "WEEK" -> {
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                int year = date.get(weekFields.weekBasedYear());
                yield year + "-W" + String.format("%02d", weekNumber);
            }
            case "MONTH" -> date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            case "YEAR" -> String.valueOf(date.getYear());
            default -> throw new BadRequestException("Invalid period type.");
        };
    }



    public Integer newCustomerRegister(@NotNullParam TimeDto timeDto) {
        List<Customer> customers = customerService.getAllBetweenAddress(timeDto.getStartDate(),timeDto.getEndDate());
        return customers.size();
    }

    public List<ProductSmallSellDto> getSellProduct(@NotNullParam TimeDto timeDto) {
        List<Sell> sells = sellRepository.findAll(
                hasDateBetween(timeDto.getStartDate(), timeDto.getEndDate())
        );

        // Ürünleri productId'ye göre gruplandır
        Map<Integer, ProductSmallSellDto> productMap = new HashMap<>();

        for (Sell sell : sells) {
            Integer productId = sell.getProduct().getId();
            ProductSmallSellDto dto = productMap.get(productId);

            if (dto == null) {
                dto = new ProductSmallSellDto(
                        productId,
                        sell.getProduct().getProductName(),
                        sell.getProduct().getProductLinkName(),
                        new ImageDetailDto(
                                sell.getProduct().getCoverImage().getId(),
                                sell.getProduct().getCoverImage().getName(),
                                sell.getProduct().getCoverImage().getResolution(),
                                sell.getProduct().getCoverImage().getName(),
                                sell.getProduct().getCoverImage().getUrl(),
                                0
                        ),
                        sell.getQuantity()
                );
                productMap.put(productId, dto);
            } else {
                dto.setQuantity(dto.getQuantity() + sell.getQuantity());
            }
        }

        return new ArrayList<>(productMap.values())
                .stream()
                .sorted(Comparator.comparing(ProductSmallSellDto::getQuantity).reversed()) // Azalan sırada
                .collect(Collectors.toList());
    }

    public List<ProductCardItemDto> cardItemContainsProduct() {
        List<CardItem> cardItems = cardItemService.findAll();

        Map<Product,ProductCardItemDto> productListMap = new HashMap<>();

        for (CardItem cardItem : cardItems) {
            Product product = cardItem.getProduct();
            ProductCardItemDto productCardItemDto = productListMap.get(product);

            if (productCardItemDto == null) {
                ProductCardItemDto dto = new ProductCardItemDto(
                        cardItem.getProduct().getId(),
                        cardItem.getProduct().getProductName(),
                        new ImageDetailDto(
                                cardItem.getProduct().getCoverImage().getId(),
                                cardItem.getProduct().getCoverImage().getName(),
                                cardItem.getProduct().getCoverImage().getResolution(),
                                cardItem.getProduct().getCoverImage().getName(),
                                cardItem.getProduct().getCoverImage().getUrl(),
                                0
                        ),
                        1
                );
                productListMap.put(product, dto);
            }else {
                productCardItemDto.setQuantity(productCardItemDto.getQuantity() + 1);
            }
        }
        return new ArrayList<>(productListMap.values())
                .stream()
                .sorted(Comparator.comparing(ProductCardItemDto::getQuantity))
                .collect(Collectors.toList());
    }

    public List<ProductAdminDetailDto> lowStock(){
        List<Product> products = productService.findAll();
        List<Product> stockNotificationProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity()<= product.getStockNotification()){
                stockNotificationProducts.add(product);
            }
        }
        return productService.productToProductAdminDetailDto(stockNotificationProducts);
    }



    private Specification<Sell> getSellProductsFilter(ProductSellFilterRequestDto productSellFilterRequestDto) {
        return Specification.where(hasProducts(productSellFilterRequestDto.getProductId()))
                .and(hasStartDate(productSellFilterRequestDto.getStartDate()))
                .and(hasEndDate(productSellFilterRequestDto.getEndDate()));
    }




    public Specification<Sell> hasProducts(Integer productId) {
        return (Root<Sell> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (productId == null || productId == 0)
                return null; // filtreleme yapılmasın
            return cb.equal(root.get("product").get("id"), productId);
        };
    }

    public Specification<Sell> hasStartDate(Instant startDate) {
        return (Root<Sell> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                startDate == null ? null : cb.greaterThanOrEqualTo(root.get("sellDate"), startDate);
    }

    public Specification<Sell> hasEndDate(Instant endDate) {
        return (Root<Sell> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                endDate == null ? null : cb.lessThanOrEqualTo(root.get("sellDate"), endDate);
    }

    public Specification<Sell> hasDateBetween(Instant start, Instant end) {
        return (Root<Sell> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            if (start != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("sellDate"), start));
            }
            if (end != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("sellDate"), end));
            }
            return predicate;
        };
    }


}
