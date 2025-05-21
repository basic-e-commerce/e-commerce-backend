package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.product.order.OrderBuilder;
import com.example.ecommercebackend.dto.product.order.*;
import com.example.ecommercebackend.dto.user.address.AddressOrderCreateDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.invoice.CorporateInvoice;
import com.example.ecommercebackend.entity.product.invoice.IndividualInvoice;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
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
import com.example.ecommercebackend.service.invoice.InvoiceService;
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
import java.math.RoundingMode;
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
    private final InvoiceService invoiceService;



    public OrderService(OrderRepository orderRepository, GuestService guestService, OrderStatusService orderStatusService, OrderItemService orderItemService, ProductService productService, OrderBuilder orderBuilder, MerchantService merchantService, CustomerRepository customerRepository, InvoiceService invoiceService) {
        this.orderRepository = orderRepository;
        this.guestService = guestService;
        this.orderStatusService = orderStatusService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.orderBuilder = orderBuilder;
        this.merchantService = merchantService;
        this.customerRepository = customerRepository;
        this.invoiceService = invoiceService;
    }


    @Transactional
    public Order createOrder(@NotNullParam OrderCreateDto orderCreateDto) {
        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        OrderStatus orderStatus = orderStatusService.createOrderStatus(OrderStatus.Status.PENDING, OrderStatus.Privacy.PUBLIC,OrderStatus.Color.RED);
        if (principal instanceof Customer customer) {

            if (customer.getCard().getItems().isEmpty())
                throw new BadRequestException("Lütfen Sepete Ürün Ekleyiniz");

            Set<OrderItem> savedOrderItems = createOrderItemWithCustomer(customer.getCard());
            BigDecimal orderPrice = orderPrice(savedOrderItems);
            BigDecimal totalPrice = processTotalPrice(orderPrice);
            BigDecimal totalTax = calculateTax(savedOrderItems);
            Invoice invoice = getInvoice(totalPrice,totalTax,orderCreateDto);
            Invoice saveInvoicce = invoiceService.save(invoice);
            Order order = saveOrder(customer, orderCreateDto.getInvoiceAddress(), savedOrderItems, orderStatus, totalPrice, orderPrice,saveInvoicce);
            return orderRepository.save(order);
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            if (orderCreateDto.getOrderItemCreateDtos().isEmpty())
                throw new BadRequestException("Lütfen Sepete Ürün Ekleyiniz");
            for (OrderItemCreateDto item : orderCreateDto.getOrderItemCreateDtos()) {
                if (item.quantity() <= 0) {
                    throw new BadRequestException("Sepette 0 ve altında ürün sayısı bulunamaz");
                }
            }

            if (customerRepository.findByUsername(orderCreateDto.getAddress().username()).isPresent()){
                Customer customer = customerRepository.findByUsername(orderCreateDto.getAddress().username()).get();
                Set<OrderItem> savedOrderItems = createOrderItemWithAnon(orderCreateDto);

                BigDecimal orderPrice = orderPrice(savedOrderItems);
                BigDecimal totalPrice = processTotalPrice(orderPrice);
                BigDecimal totalTax = calculateTax(savedOrderItems);
                Invoice invoice = getInvoice(totalPrice,totalTax,orderCreateDto);
                Invoice saveInvoicce = invoiceService.save(invoice);
                Order order = saveOrder(customer, orderCreateDto.getAddress(), savedOrderItems, orderStatus, totalPrice, orderPrice, saveInvoicce);
                return orderRepository.save(order);

            }else {
                Guest guest = guestService.findByUsernameOrNull(orderCreateDto.getAddress().username());
                if (guest == null){
                    guest = guestService.save(orderCreateDto.getAddress().firstName(),
                            orderCreateDto.getAddress().lastName(),
                            orderCreateDto.getAddress().phoneNo(),
                            orderCreateDto.getAddress().username(),
                            false,
                            false);

                }
                Set<OrderItem> savedOrderItems = createOrderItemWithAnon(orderCreateDto);
                BigDecimal orderPrice = orderPrice(savedOrderItems);
                BigDecimal totalPrice = processTotalPrice(orderPrice);
                BigDecimal totalTax = calculateTax(savedOrderItems);
                Invoice invoice = getInvoice(totalPrice,totalTax,orderCreateDto);
                Invoice saveInvoicce = invoiceService.save(invoice);
                Order order = saveOrder(guest, orderCreateDto.getAddress(), savedOrderItems, orderStatus, totalPrice, orderPrice, saveInvoicce);
                return orderRepository.save(order);
            }
        }else
            throw new BadRequestException("Geçersiz Kullanıcı");
    }

    private BigDecimal calculateTax(Set<OrderItem> savedOrderItems) {
        BigDecimal totalTaxAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItem item : savedOrderItems) {
            BigDecimal price = item.getPrice();
            Integer quantity = item.getQuantity();
            BigDecimal taxRate = item.getProduct().getTaxRate();

            if (taxRate == null) {
                taxRate = BigDecimal.ZERO;
            }

            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal itemTax = itemTotal.multiply(taxRate);

            totalAmount = totalAmount.add(itemTotal);
            totalTaxAmount = totalTaxAmount.add(itemTax);
        }

        if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Bölme hatasını önler
        }

        return totalTaxAmount.divide(totalAmount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal orderPrice(Set<OrderItem> orderItems) {
        BigDecimal orderPrice = BigDecimal.valueOf(0);
        for (OrderItem orderItem: orderItems) {
            orderPrice = orderPrice.add(orderItem.getPrice());
        }
        return orderPrice;
    }

    public Set<OrderItem> createOrderItemWithCustomer(Card card) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CardItem cardItem: card.getItems()){
            BigDecimal orderItemPrice = cardItem.getProduct().getComparePrice().multiply(new BigDecimal(cardItem.getQuantity()));
            OrderItem orderItem = new OrderItem(cardItem.getProduct(),orderItemPrice, cardItem.getQuantity());
            orderItems.add(orderItem);
        }

        return orderItemService.saveOrderItems(orderItems);
    }

    public Set<OrderItem> createOrderItemWithAnon(OrderCreateDto orderCreateDto) {
        Set<OrderItem> orderItems = new HashSet<>();

        for (OrderItemCreateDto orderItemCreateDto: orderCreateDto.getOrderItemCreateDtos()){
            Product product = productService.findProductById(orderItemCreateDto.productId());

            if (product.getQuantity() < orderItemCreateDto.quantity())
                throw new BadRequestException("Yetersiz Ürün Stoğu: "+product.getProductName());

            BigDecimal orderItemPrice = product.getComparePrice().multiply(new BigDecimal(orderItemCreateDto.quantity()));
            OrderItem orderItem = new OrderItem(product,orderItemPrice, orderItemCreateDto.quantity());
            orderItems.add(orderItem);
        }

        return orderItemService.saveOrderItems(orderItems);
    }



    public Order saveOrder(User user, AddressOrderCreateDto addressOrderCreateDto, Set<OrderItem> orderItems, OrderStatus orderStatus, BigDecimal totalPrice, BigDecimal orderPrice,Invoice invoice) {
        return new Order(user,
                null,
                addressOrderCreateDto.firstName(),
                addressOrderCreateDto.lastName(),
                addressOrderCreateDto.username(),
                addressOrderCreateDto.countryName(),
                addressOrderCreateDto.city(),
                addressOrderCreateDto.addressLine1(),
                addressOrderCreateDto.postalCode(),
                addressOrderCreateDto.phoneNo(),
                orderItems,
                orderStatus,
                totalPrice,
                orderPrice,
                invoice);

    }

    public Invoice getInvoice(BigDecimal totalPrice,BigDecimal totalTax,OrderCreateDto orderCreateDto){
        Invoice invoice;
        if (Invoice.InvoiceType.CORPORATE == Invoice.InvoiceType.valueOf(orderCreateDto.getInvoiceType())){
            if (orderCreateDto.getDiffAddress()){
                if (orderCreateDto.getCorporateInvoice()!= null){
                    return new CorporateInvoice(
                            null,
                            totalPrice,
                            totalTax,
                            Invoice.InvoiceType.CORPORATE,
                            orderCreateDto.getInvoiceAddress().firstName(),
                            orderCreateDto.getInvoiceAddress().lastName(),
                            orderCreateDto.getInvoiceAddress().username(),
                            orderCreateDto.getInvoiceAddress().countryName(),
                            orderCreateDto.getInvoiceAddress().city(),
                            orderCreateDto.getInvoiceAddress().addressLine1(),
                            orderCreateDto.getInvoiceAddress().postalCode(),
                            orderCreateDto.getInvoiceAddress().phoneNo(),
                            orderCreateDto.getCorporateInvoice().companyName(),
                            orderCreateDto.getCorporateInvoice().taxNumber(),
                            orderCreateDto.getCorporateInvoice().taxOffice());
                }else
                    throw new BadRequestException("Farklı address bulunamamaktadır");

            }else{
                return new CorporateInvoice(
                        null,
                        totalPrice,
                        totalTax,
                        Invoice.InvoiceType.CORPORATE,
                        orderCreateDto.getAddress().firstName(),
                        orderCreateDto.getAddress().lastName(),
                        orderCreateDto.getAddress().username(),
                        orderCreateDto.getAddress().countryName(),
                        orderCreateDto.getAddress().city(),
                        orderCreateDto.getAddress().addressLine1(),
                        orderCreateDto.getAddress().postalCode(),
                        orderCreateDto.getAddress().phoneNo(),
                        orderCreateDto.getCorporateInvoice().companyName(),
                        orderCreateDto.getCorporateInvoice().taxNumber(),
                        orderCreateDto.getCorporateInvoice().taxOffice());
            }

        } else if (Invoice.InvoiceType.INDIVIDUAL == Invoice.InvoiceType.valueOf(orderCreateDto.getInvoiceType())) {
            if (orderCreateDto.getDiffAddress()){
                if (orderCreateDto.getInvoiceAddress() != null){
                    return new IndividualInvoice(
                            null,
                            totalPrice,
                            totalTax,
                            Invoice.InvoiceType.INDIVIDUAL,
                            orderCreateDto.getInvoiceAddress().firstName(),
                            orderCreateDto.getInvoiceAddress().lastName(),
                            orderCreateDto.getInvoiceAddress().username(),
                            orderCreateDto.getInvoiceAddress().countryName(),
                            orderCreateDto.getInvoiceAddress().city(),
                            orderCreateDto.getInvoiceAddress().addressLine1(),
                            orderCreateDto.getInvoiceAddress().postalCode(),
                            orderCreateDto.getInvoiceAddress().phoneNo());
                }else
                    throw new BadRequestException("Farklı address bulunamamaktadır");
            }else{
                return new IndividualInvoice(
                        null,
                        totalPrice,
                        totalTax,
                        Invoice.InvoiceType.INDIVIDUAL,
                        orderCreateDto.getAddress().firstName(),
                        orderCreateDto.getAddress().lastName(),
                        orderCreateDto.getAddress().username(),
                        orderCreateDto.getAddress().countryName(),
                        orderCreateDto.getAddress().city(),
                        orderCreateDto.getAddress().addressLine1(),
                        orderCreateDto.getAddress().postalCode(),
                        orderCreateDto.getAddress().phoneNo());
            }
        }else
            throw new BadRequestException("Geçersiz Fatura Tipi");
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
        return orderRepository.findAll(filterOrders(orderFilterRequest.getPaymentStatus()),pageable).stream().map(orderBuilder::orderToOrderDetailDto).collect(Collectors.toList());
    }

    public List<Order> filterGuestSuccessOrder(User user){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Specification<Order> specification = filterGuestSuccessOrders(user);
        return orderRepository.findAll(specification,sort);
    }

    private Specification<Order> filterOrders(String status) {
        if (!(status == null || status.isEmpty()))
            return Specification.where(hasStatus(OrderStatus.Status.valueOf(status)));
        else
            return Specification.where(hasStatus(null));
    }

    private Specification<Order> filterGuestSuccessOrders(User user) {
        return Specification.where(hasStatus(OrderStatus.Status.APPROVED)).and(hasUser(user));
    }



    public BigDecimal getTotalPrice(Instant startDate, Instant endDate) {
        return orderRepository.findSuccessTotalPriceBetweenDates(OrderStatus.Status.APPROVED,startDate, endDate);
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

    public List<OrderDetailDto> findSuccessOrderByUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Customer customer) {
            return filterGuestSuccessOrder(customer).stream().map(orderBuilder::orderToOrderDetailDto).collect(Collectors.toList());
        }else
            throw new BadRequestException("User Not Authanticated");

    }










    public Specification<Order> hasStatus(OrderStatus.Status status) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (status == null) {
                return null; // Bu durumda filtre uygulanmaz
            }
            Join<Order, OrderStatus> statusJoin = root.join("orderStatus");
            return cb.equal(statusJoin.get("status"), status);
        };
    }

    public Specification<Order> hasUser(User user) {
        return (Root<Order> root,CriteriaQuery<?> query,CriteriaBuilder cb) ->
                user == null ? null : cb.equal(root.get("user"), user);
    }

    public List<OrderDetailDto> getAll() {
        return orderRepository.findAll().stream().map(orderBuilder::orderToOrderDetailDto).collect(Collectors.toList());
    }
}
