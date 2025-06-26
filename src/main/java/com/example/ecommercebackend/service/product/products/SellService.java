package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.builder.product.sell.SellBuilder;
import com.example.ecommercebackend.dto.product.sell.ProductDaySell;
import com.example.ecommercebackend.dto.product.sell.ProductSellDayFilterRequestDto;
import com.example.ecommercebackend.dto.product.sell.ProductSellDto;
import com.example.ecommercebackend.dto.product.sell.ProductSellFilterRequestDto;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.product.products.SellRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SellService {
    private final SellRepository sellRepository;
    private final ProductService productService;
    private final SellBuilder sellBuilder;

    public SellService(SellRepository sellRepository, ProductService productService, SellBuilder sellBuilder) {
        this.sellRepository = sellRepository;
        this.productService = productService;
        this.sellBuilder = sellBuilder;
    }

    @Transactional
    public Sell save(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        System.out.println("sell product: " + product.getProductName());
        System.out.println("sell product quantity: : " + product.getQuantity());

        System.out.println("orderItem quantity: : " + orderItem.getQuantity());
        System.out.println("kalan quantity: " + (product.getQuantity() - orderItem.getQuantity()));

        product.setQuantity(product.getQuantity() - orderItem.getQuantity());
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

    public List<ProductDaySell> getSellProductsDaySell(ProductSellDayFilterRequestDto filter) {
        ZoneId zoneId = ZoneId.of("Europe/Istanbul");

        LocalDate startDate = filter.getStartDate().atZone(zoneId).toLocalDate();
        LocalDate endDate = filter.getEndDate().atZone(zoneId).toLocalDate();

        // Instant tarih aralığı oluştur (günün başlangıcı ve bitişi)
        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(zoneId).toInstant().minusMillis(1);

        // Tüm satışları tek sorguda çek
        List<Sell> sells = sellRepository.findAll(Specification.where(
                hasDateBetween(startInstant, endInstant).and(hasProducts(filter.getProductId())))
        );

        Map<String, List<Sell>> sellsByPeriod = sells.stream()
                .collect(Collectors.groupingBy(sell -> {
                    LocalDate date = sell.getSellDate().atZone(zoneId).toLocalDate();

                    return switch (filter.getPeriodType().toUpperCase()) {
                        case "DAY" -> date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                        case "WEEK" -> date.getYear() + "-W" + String.format("%02d", date.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
                        case "MONTH" -> date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                        case "YEAR" -> String.valueOf(date.getYear());
                        default -> throw new IllegalArgumentException("Invalid period type. Use DAY, WEEK, MONTH, or YEAR.");
                    };
                }));


        List<ProductDaySell> report = new ArrayList<>();

        TemporalAmount step = switch (filter.getPeriodType().toUpperCase()) {
            case "DAY" -> Period.ofDays(1);
            case "WEEK" -> Period.ofWeeks(1);
            case "MONTH" -> Period.ofMonths(1);
            case "YEAR" -> Period.ofYears(1);
            default -> throw new BadRequestException("Invalid period type. Use DAY, WEEK, MONTH, or YEAR.");
        };

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plus(step)) {
            String label = switch (filter.getPeriodType().toUpperCase()) {
                case "DAY" -> date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                case "WEEK" -> date.getYear() + "-W" + String.format("%02d", date.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
                case "MONTH" -> date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                case "YEAR" -> String.valueOf(date.getYear());
                default -> throw new IllegalArgumentException("Invalid period type.");
            };

            List<Sell> periodSells = sellsByPeriod.getOrDefault(label, Collections.emptyList());

            BigDecimal totalAmount = periodSells.stream()
                    .map(sell -> sell.getPrice().multiply(BigDecimal.valueOf(sell.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int totalQuantity = periodSells.stream()
                    .mapToInt(Sell::getQuantity)
                    .sum();

            report.add(new ProductDaySell(totalAmount, totalQuantity, label));
        }
        return report;

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
