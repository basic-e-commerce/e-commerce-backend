package com.example.ecommercebackend.service.product.products;

import com.example.ecommercebackend.builder.product.sell.SellBuilder;
import com.example.ecommercebackend.dto.product.sell.ProductSellDto;
import com.example.ecommercebackend.dto.product.sell.ProductSellFilterRequestDto;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.repository.product.products.SellRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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


}
