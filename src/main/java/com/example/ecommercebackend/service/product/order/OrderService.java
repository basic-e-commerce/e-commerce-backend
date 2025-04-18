package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.builder.product.order.OrderBuilder;
import com.example.ecommercebackend.dto.product.order.*;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Guest;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.order.OrderRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.example.ecommercebackend.service.product.products.ProductService;
import com.example.ecommercebackend.service.user.GuestService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GuestService guestService;
    private final OrderStatusService orderStatusService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final OrderBuilder orderBuilder;
    private final MerchantService merchantService;
    private final CustomerRepository customerRepository;


    public OrderService(OrderRepository orderRepository, GuestService guestService, OrderStatusService orderStatusService, OrderItemService orderItemService, ProductService productService, OrderBuilder orderBuilder, MerchantService merchantService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.guestService = guestService;
        this.orderStatusService = orderStatusService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.orderBuilder = orderBuilder;
        this.merchantService = merchantService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {
        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Customer customer) {

            if (customer.getCard().getItems().isEmpty())
                throw new BadRequestException("Lütfen Sepete Ürün Ekleyiniz");

            System.out.println("1111111111111111");
            OrderStatus orderStatus = orderStatusService.createOrderStatus(OrderStatus.Status.PENDING, OrderStatus.Privacy.PUBLIC,OrderStatus.Color.RED);
            Set<OrderItem> orderItems = new HashSet<>();
            for (CardItem cardItem: customer.getCard().getItems()){
                BigDecimal orderItemPrice = cardItem.getProduct().getComparePrice().multiply(new BigDecimal(cardItem.getQuantity()));
                OrderItem orderItem = new OrderItem(cardItem.getProduct(),orderItemPrice, cardItem.getQuantity());
                orderItems.add(orderItem);
            }
            System.out.println("22222222222222222");
            Set<OrderItem> savedOrderItems = orderItemService.saveOrderItems(orderItems);
            BigDecimal orderPrice = BigDecimal.valueOf(0);
            for (OrderItem orderItem: savedOrderItems){
                orderPrice = orderPrice.add(orderItem.getPrice());
            }
            System.out.println("333333333333333333");
            BigDecimal totalPrice = processTotalPrice(orderPrice);


            Order order = new Order(customer,
                    null,
                    orderCreateDto.address().firstName(),
                    orderCreateDto.address().lastName(),
                    orderCreateDto.address().username(),
                    orderCreateDto.address().countryName(),
                    orderCreateDto.address().city(),
                    orderCreateDto.address().addressLine1(),
                    orderCreateDto.address().postalCode(),
                    orderCreateDto.address().phoneNo(),
                    savedOrderItems,
                    orderStatus,
                    totalPrice,
                    orderPrice);
            System.out.println("444444444444444444");
            Order save = orderRepository.save(order);
            System.out.println("666666666666666666");


            return orderBuilder.orderToOrderResponseDto(save);
        }


        if (authentication instanceof AnonymousAuthenticationToken) {
            if (orderCreateDto.orderItemCreateDtos().isEmpty())
                throw new BadRequestException("Lütfen Sepete Ürün Ekleyiniz");

            for (OrderItemCreateDto item : orderCreateDto.orderItemCreateDtos()) {
                if (item.quantity() <= 0) {
                    throw new BadRequestException("Sepette 0 ve altında ürün sayısı bulunamaz");
                }
            }

            if (customerRepository.findByUsername(orderCreateDto.address().username()).isPresent()){
                Customer customer = customerRepository.findByUsername(orderCreateDto.address().username()).get();

                OrderStatus orderStatus = orderStatusService.createOrderStatus(OrderStatus.Status.PENDING, OrderStatus.Privacy.PUBLIC,OrderStatus.Color.RED);
                Set<OrderItem> orderItems = new HashSet<>();
                for (CardItem cardItem: customer.getCard().getItems()){
                    BigDecimal orderItemPrice = cardItem.getProduct().getComparePrice().multiply(new BigDecimal(cardItem.getQuantity()));
                    OrderItem orderItem = new OrderItem(cardItem.getProduct(),orderItemPrice, cardItem.getQuantity());
                    orderItems.add(orderItem);
                }
                System.out.println("22222222222222222");
                Set<OrderItem> savedOrderItems = orderItemService.saveOrderItems(orderItems);
                BigDecimal orderPrice = BigDecimal.valueOf(0);
                for (OrderItem orderItem: savedOrderItems){
                    orderPrice = orderPrice.add(orderItem.getPrice());
                }
                System.out.println("333333333333333333");
                BigDecimal totalPrice = processTotalPrice(orderPrice);


                Order order = new Order(customer,
                        null,
                        orderCreateDto.address().firstName(),
                        orderCreateDto.address().lastName(),
                        orderCreateDto.address().username(),
                        orderCreateDto.address().countryName(),
                        orderCreateDto.address().city(),
                        orderCreateDto.address().addressLine1(),
                        orderCreateDto.address().postalCode(),
                        orderCreateDto.address().phoneNo(),
                        savedOrderItems,
                        orderStatus,
                        totalPrice,
                        orderPrice);
                System.out.println("444444444444444444");
                Order save = orderRepository.save(order);
                System.out.println("666666666666666666");


                return orderBuilder.orderToOrderResponseDto(save);

            }else {
                Guest guest = guestService.findByUsernameOrNull(orderCreateDto.address().username());

                if (guest == null){
                    guest = guestService.save(orderCreateDto.address().firstName(),
                            orderCreateDto.address().lastName(),
                            orderCreateDto.address().phoneNo(),
                            orderCreateDto.address().username(),
                            false,
                            false);

                    System.out.println("1111111111111111");
                }

                System.out.println("1111111111111111");
                OrderStatus orderStatus = orderStatusService.createOrderStatus(OrderStatus.Status.PENDING, OrderStatus.Privacy.PUBLIC,OrderStatus.Color.RED);
                Set<OrderItem> orderItems = new HashSet<>();

                for (OrderItemCreateDto orderItemCreateDto: orderCreateDto.orderItemCreateDtos()){
                    Product product = productService.findProductById(orderItemCreateDto.productId());

                    if (product.getQuantity() < orderItemCreateDto.quantity())
                        throw new BadRequestException("Yetersiz Ürün Stoğu: "+product.getProductName());

                    BigDecimal orderItemPrice = product.getComparePrice().multiply(new BigDecimal(orderItemCreateDto.quantity()));
                    OrderItem orderItem = new OrderItem(product,orderItemPrice, orderItemCreateDto.quantity());
                    orderItems.add(orderItem);
                }

                System.out.println("22222222222222222");
                Set<OrderItem> savedOrderItems = orderItemService.saveOrderItems(orderItems);
                BigDecimal orderPrice = BigDecimal.valueOf(0);
                for (OrderItem orderItem: savedOrderItems){
                    orderPrice = orderPrice.add(orderItem.getPrice());
                }

                BigDecimal totalPrice = processTotalPrice(orderPrice);
                System.out.println("totalPrice = " + totalPrice);

                System.out.println("333333333333333333");

                Order order = new Order(guest,
                        null,
                        orderCreateDto.address().firstName(),
                        orderCreateDto.address().lastName(),
                        orderCreateDto.address().username(),
                        orderCreateDto.address().countryName(),
                        orderCreateDto.address().city(),
                        orderCreateDto.address().addressLine1(),
                        orderCreateDto.address().postalCode(),
                        orderCreateDto.address().phoneNo(),
                        savedOrderItems,
                        orderStatus,
                        totalPrice,
                        orderPrice);

                System.out.println("444444444444444444");
                Order save = orderRepository.save(order);
                System.out.println("666666666666666666");

                return orderBuilder.orderToOrderResponseDto(save);
            }



        }else
            throw new BadRequestException("Geçersiz Kullanıcı");
    }

    private BigDecimal processTotalPrice(BigDecimal totalPrice) {
        Merchant merchant = merchantService.getMerchant();
        BigDecimal kargoPrice = merchant.getShippingFee();
        System.out.println("******************** kargoprice : "+ kargoPrice);
        BigDecimal minPrice = merchant.getMinOrderAmount();
        System.out.println("******************** min priceeeee : "+minPrice);

        System.out.println("*********** karşılaştırma: "+totalPrice.compareTo(minPrice));

        if (totalPrice.compareTo(minPrice) < 0) {
            totalPrice = totalPrice.add(kargoPrice);
        }
        System.out.println("************************processTotalPrice: "+totalPrice);
        return totalPrice;
    }

    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode).orElseThrow(()-> new NotFoundException("Order "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public OrderStatus updateOrderStatus(OrderStatus orderStatus) {
        return orderStatusService.updateOrderStatus(orderStatus);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<OrderDetailDto> filterOrder(OrderFilterRequest orderFilterRequest, int page, int size) {
        Sort sort = Sort.unsorted();
        if (orderFilterRequest.getSortBy() != null) {
            sort = Sort.by(Sort.Direction.fromString(orderFilterRequest.getSortDirection()), orderFilterRequest.getSortBy());
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Order> specification = filterOrders();
        return orderRepository.findAll(specification,pageable).stream().map(orderBuilder::orderToOrderDetailDto).collect(Collectors.toList());
    }

    public List<Order> filterGuestSuccessOrder(User user){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Specification<Order> specification = filterGuestSuccessOrders(user);
        return orderRepository.findAll(specification,sort);
    }

    private Specification<Order> filterOrders() {
        return Specification.where(hasStatus(OrderStatus.Status.APPROVED));
    }

    private Specification<Order> filterGuestSuccessOrders(User user) {
        return Specification.where(hasStatus(OrderStatus.Status.APPROVED)).and(hasUser(user));
    }

    public Specification<Order> hasStatus(OrderStatus.Status status) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<Order, OrderStatus> statusJoin = root.join("orderStatus");
            return cb.equal(statusJoin.get("status"), status);
        };
    }

    public Specification<Order> hasUser(User user) {
        return (Root<Order> root,CriteriaQuery<?> query,CriteriaBuilder cb) ->
                user == null ? null : cb.equal(root.get("user"), user);
    }

    public BigDecimal getTotalPrice(Instant startDate, Instant endDate) {
        return orderRepository.findTotalPriceBetweenDates(startDate, endDate);
    }


    public List<Order> changeSuccessGuestNull(Guest guest) {
        Specification<Order> getGuestSuccessOrder = Specification.where(hasStatus(OrderStatus.Status.APPROVED)).and(hasUser(guest));
        List<Order> all = orderRepository.findAll(getGuestSuccessOrder);
        for (Order order : all) {
            order.setUser(null);
            orderRepository.save(order);
        }
        return all;
    }


    public void changeSuccessCustomerOrder(Customer save, List<Order> guestOrders) {
        for (Order order : guestOrders) {
            order.setUser(save);
            orderRepository.save(order);
        }
    }

    public List<OrderResponseDto> findSuccessOrderByUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Customer customer) {
            return filterGuestSuccessOrder(customer).stream().map(orderBuilder::orderToOrderResponseDto).collect(Collectors.toList());
        }else
            throw new BadRequestException("USer Not Authanticated");

    }
}
