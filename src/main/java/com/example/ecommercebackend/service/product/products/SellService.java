package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.builder.product.sell.SellBuilder;
import com.example.ecommercebackend.dto.product.sell.*;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.product.products.SellRepository;
import com.example.ecommercebackend.service.payment.PaymentService;
import com.example.ecommercebackend.service.product.order.OrderService;
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
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SellService {
    private final SellRepository sellRepository;
    private final ProductService productService;
    private final SellBuilder sellBuilder;
    private final OrderService orderService;

    public SellService(SellRepository sellRepository, ProductService productService, SellBuilder sellBuilder, OrderService orderService) {
        this.sellRepository = sellRepository;
        this.productService = productService;
        this.sellBuilder = sellBuilder;
        this.orderService = orderService;
    }

    @Transactional
    public Sell save(OrderItem orderItem) {
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
                orderItem.getPrice(),
                orderItem.getQuantity()
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

        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(zoneId).toInstant().minusMillis(1);

        // Minimum tarih kontrolü (opsiyonel)
        LocalDate minDate = LocalDate.of(2025, 6, 1);
        Instant minInstant = minDate.atStartOfDay(zoneId).toInstant();

        if (startInstant.isBefore(minInstant)) {
            startInstant = minInstant;
        }

        // Orderları getir
        List<Order> orders = orderService.findSuccessOrderBetweenDates(startInstant, endInstant);

        // Periyoda göre gruplama
        Map<String, List<Order>> ordersByPeriod = orders.stream()
                .filter(order -> !order.getCreatedAt().isBefore(minInstant))
                .collect(Collectors.groupingBy(order -> {
                    LocalDate date = order.getCreatedAt().atZone(zoneId).toLocalDate();
                    String periodType = filter.getPeriodType().toUpperCase();

                    return switch (periodType) {
                        case "DAY" -> date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                        case "WEEK" -> date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                                .format(DateTimeFormatter.ISO_LOCAL_DATE);
                        case "MONTH" -> date.with(TemporalAdjusters.lastDayOfMonth())
                                .format(DateTimeFormatter.ISO_LOCAL_DATE);
                        case "YEAR" -> date.with(TemporalAdjusters.lastDayOfYear())
                                .format(DateTimeFormatter.ISO_LOCAL_DATE);
                        default -> throw new IllegalArgumentException("Invalid period type.");
                    };
                }));

        // Step belirleme
        TemporalAmount step = switch (filter.getPeriodType().toUpperCase()) {
            case "DAY" -> Period.ofDays(1);
            case "WEEK" -> Period.ofWeeks(1);
            case "MONTH" -> Period.ofMonths(1);
            case "YEAR" -> Period.ofYears(1);
            default -> throw new BadRequestException("Invalid period type.");
        };

        // Başlangıç tarih güncelleme (min kontrolü)
        LocalDate minInstantToLocalDate = minInstant.atZone(zoneId).toLocalDate();
        if (minInstantToLocalDate.isAfter(startDate)) {
            startDate = minInstantToLocalDate;
        }

        List<ProductDaySell> report = new ArrayList<>();

        // Periyodlara göre rapor oluşturma
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plus(step)) {
            String label = switch (filter.getPeriodType().toUpperCase()) {
                case "DAY" -> date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                case "WEEK" -> date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE);
                case "MONTH" -> date.with(TemporalAdjusters.lastDayOfMonth())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE);
                case "YEAR" -> date.with(TemporalAdjusters.lastDayOfYear())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE);
                default -> throw new IllegalArgumentException("Invalid period type.");
            };

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

        // Genel toplamlar
        int totalQuantity = report.stream()
                .map(ProductDaySell::getQuantity)
                .reduce(0, Integer::sum);

        BigDecimal totalPrice = report.stream()
                .map(ProductDaySell::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Ortalama sepet tutarı
        int totalSuccessOrder = orders.size();
        BigDecimal averageOrderAmount = totalSuccessOrder == 0
                ? BigDecimal.ZERO
                : totalPrice.divide(BigDecimal.valueOf(totalSuccessOrder), 2, RoundingMode.HALF_UP);

        return new ProductDaySellAdmin(
                report.stream().sorted(Comparator.comparing(ProductDaySell::getDate)).toList(),
                totalQuantity,
                totalPrice,
                totalSuccessOrder,
                averageOrderAmount
        );
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

    public static Specification<Sell> hasDateBetween(Instant start, Instant end) {
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


    public Specification<Sell> hasDate(LocalDate date) {
        return (Root<Sell> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (date == null) {
                return null;
            }

            ZoneId zoneId = ZoneId.of("Europe/Istanbul");

            Instant startOfDay = date.atStartOfDay(zoneId).toInstant();
            Instant endOfDay = date.plusDays(1).atStartOfDay(zoneId).toInstant();

            return cb.between(root.get("sellDate"), startOfDay, endOfDay);
        };
    }





}
