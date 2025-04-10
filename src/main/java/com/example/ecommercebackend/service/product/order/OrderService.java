package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.builder.product.order.OrderBuilder;
import com.example.ecommercebackend.dto.product.order.OrderCreateDto;
import com.example.ecommercebackend.dto.product.order.OrderItemCreateDto;
import com.example.ecommercebackend.dto.product.order.OrderResponseDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Guest;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.order.OrderRepository;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.example.ecommercebackend.service.product.products.ProductService;
import com.example.ecommercebackend.service.user.GuestService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GuestService guestService;
    private final OrderStatusService orderStatusService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final OrderBuilder orderBuilder;
    private final MerchantService merchantService;


    public OrderService(OrderRepository orderRepository, GuestService guestService, OrderStatusService orderStatusService, OrderItemService orderItemService, ProductService productService, OrderBuilder orderBuilder, MerchantService merchantService) {
        this.orderRepository = orderRepository;
        this.guestService = guestService;
        this.orderStatusService = orderStatusService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.orderBuilder = orderBuilder;
        this.merchantService = merchantService;
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
                    totalPrice);

            System.out.println("444444444444444444");
            Order save = orderRepository.save(order);
            System.out.println("666666666666666666");

            return orderBuilder.orderToOrderResponseDto(save);

        }
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


    public Order findByPayment(Payment payment) {
        return orderRepository.findByPaymentsContaining(payment).orElseThrow(()-> new NotFoundException("Order "+ ExceptionMessage.NOT_FOUND.getMessage()+ " for payment"));
    }

    public OrderStatus updateOrderStatus(OrderStatus orderStatus) {
        return orderStatusService.updateOrderStatus(orderStatus);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

}
