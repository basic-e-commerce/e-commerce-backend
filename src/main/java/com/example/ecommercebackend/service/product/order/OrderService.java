package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.dto.product.order.OrderCreateDto;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.product.order.OrderRepository;
import com.example.ecommercebackend.service.user.CustomerService;
import com.example.ecommercebackend.service.user.GuestService;
import jakarta.transaction.Transactional;
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
    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepository, GuestService guestService, CustomerService customerService, OrderStatusService orderStatusService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.guestService = guestService;
        this.customerService = customerService;
        this.orderStatusService = orderStatusService;
        this.orderItemService = orderItemService;
    }

    @Transactional
    public Order createOrder(OrderCreateDto orderCreateDto) {
        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı bilgilerini principal üzerinden alıyoruz
        Object principal = authentication.getPrincipal();

        if (principal instanceof Customer customer) {

            if (customer.getCard().getItems().isEmpty())
                throw new BadRequestException("Lütfen Sepete Ürün Ekleyiniz");

            OrderStatus orderStatus = orderStatusService.createOrderStatus(OrderStatus.Status.PENDING, OrderStatus.Privacy.PUBLIC,OrderStatus.Color.RED);
            Set<OrderItem> orderItems = new HashSet<>();
            for (CardItem cardItem: customer.getCard().getItems()){
                BigDecimal orderItemPrice = cardItem.getProduct().getComparePrice().multiply(new BigDecimal(cardItem.getQuantity()));
                OrderItem orderItem = new OrderItem(cardItem.getProduct(),orderItemPrice, cardItem.getQuantity());
                orderItems.add(orderItem);
            }
            Set<OrderItem> savedOrderItems = orderItemService.saveOrderItems(orderItems);
            BigDecimal orderPrice = BigDecimal.valueOf(0);
            for (OrderItem orderItem: savedOrderItems){
                orderPrice = orderPrice.add(orderItem.getPrice());
            }


            Order order = new Order(null,
                    orderCreateDto.address().firstName(),
                    orderCreateDto.address().lastName(),
                    orderCreateDto.address().countryName(),
                    orderCreateDto.address().city(),
                    orderCreateDto.address().addressLine1(),
                    orderCreateDto.address().addressLine2(),
                    orderCreateDto.address().postalCode(),
                    orderCreateDto.address().phoneNo(),
                    savedOrderItems,
                    orderStatus,
                    orderPrice);

            Order save = orderRepository.save(order);
            customer.getOrders().add(save);
            customerService.save(customer);
            return save;
        }else
            return null;
    }


}
