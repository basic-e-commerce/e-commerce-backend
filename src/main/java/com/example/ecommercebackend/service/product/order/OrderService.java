package com.example.ecommercebackend.service.product.order;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.product.order.OrderBuilder;
import com.example.ecommercebackend.dto.product.order.*;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemResponseDto;
import com.example.ecommercebackend.dto.product.products.productTemplate.CargoOfferDesiRequestAdminDto;
import com.example.ecommercebackend.dto.product.shipping.*;
import com.example.ecommercebackend.dto.user.address.AddressOrderCreateDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.card.CardItem;
import com.example.ecommercebackend.entity.product.invoice.CorporateInvoice;
import com.example.ecommercebackend.entity.product.invoice.IndividualInvoice;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Coupon;
import com.example.ecommercebackend.entity.product.products.CustomerCoupon;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Guest;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.product.order.OrderRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.service.invoice.InvoiceService;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.example.ecommercebackend.service.product.products.CouponService;
import com.example.ecommercebackend.service.product.products.CustomerCouponService;
import com.example.ecommercebackend.service.product.products.ProductService;
import com.example.ecommercebackend.service.product.shipping.CountryService;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import com.example.ecommercebackend.service.product.shipping.ShippingCargoService;
import com.example.ecommercebackend.service.user.CityService;
import com.example.ecommercebackend.service.user.DistrictService;
import com.example.ecommercebackend.service.user.GuestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final GuestService guestService;
    private final OrderStatusService orderStatusService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final OrderBuilder orderBuilder;
    private final MerchantService merchantService;
    private final CustomerRepository customerRepository;
    private final InvoiceService invoiceService;
    private final CustomerCouponService customerCouponService;
    private final ShippingAddressService shippingAddressService;
    private final ShippingCargoService shippingCargoService;
    private final OrderPackageService orderPackageService;
    private final CouponService couponService;

    @Value("${domain.test}")
    private String domain;



    public OrderService(OrderRepository orderRepository, GuestService guestService, OrderStatusService orderStatusService, OrderItemService orderItemService, ProductService productService, OrderBuilder orderBuilder, MerchantService merchantService, CustomerRepository customerRepository, InvoiceService invoiceService, CustomerCouponService customerCouponService, ShippingAddressService shippingAddressService, ShippingCargoService shippingCargoService, OrderPackageService orderPackageService, CouponService couponService) {
        this.orderRepository = orderRepository;
        this.guestService = guestService;
        this.orderStatusService = orderStatusService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.orderBuilder = orderBuilder;
        this.merchantService = merchantService;
        this.customerRepository = customerRepository;
        this.invoiceService = invoiceService;
        this.customerCouponService = customerCouponService;
        this.shippingAddressService = shippingAddressService;
        this.shippingCargoService = shippingCargoService;
        this.orderPackageService = orderPackageService;
        this.couponService = couponService;
    }


    @Transactional
    public Order createOrder(@NotNullParam OrderCreateDto orderCreateDto) {
        // Authentication nesnesini güvenlik bağlamından alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        OrderStatus orderStatus = orderStatusService.createOrderStatus(OrderStatus.Status.PENDING, OrderStatus.Privacy.PUBLIC,OrderStatus.Color.RED);
        if (principal instanceof Customer customer) {
            System.out.println("OrderCode: "+ orderCreateDto.getCode());

            Set<OrderItem> savedOrderItems = createOrderItemWithCustomer(customer.getCard());

            CustomerCoupon customerCoupon = null;
            Coupon coupon = null;
            if (orderCreateDto.getCode() != null && !orderCreateDto.getCode().isEmpty()) {
                customerCoupon = customerCouponService.findCouponByCodeAndActive(orderCreateDto.getCode(), customer);

                if (customerCoupon != null) {
                    if (customerCoupon.getUsed())
                        throw new ResourceAlreadyExistException("Bu kupon kullanılmıştır!");
                }else {
                    customerCoupon = new CustomerCoupon(
                            customer,
                            coupon,
                            Instant.now()
                    );
                }
                coupon = couponService.findByCodeNull(orderCreateDto.getCode());
                customerCoupon.setCoupon(coupon);
                if (coupon != null) {
                    isCouponValidation(coupon,savedOrderItems,customer);
                }
            }

            if (customer.getCard().getItems().isEmpty())
                throw new BadRequestException("Lütfen Sepete Ürün Ekleyiniz");

            BigDecimal orderPrice = orderPrice(savedOrderItems,coupon);

            TotalProcessDto totalPriceDto = processTotalPrice(savedOrderItems);

            BigDecimal totalTax = calculateTax(totalPriceDto.getSavedOrderItems());

            Invoice invoice = getInvoice(totalPriceDto.getTotalPrice(),totalTax,orderCreateDto);

            Invoice saveInvoicce = invoiceService.save(invoice);

            Order order = saveOrder(
                    customer,
                    orderCreateDto.getAddress(),
                    totalPriceDto.getSavedOrderItems(),
                    orderStatus,
                    totalPriceDto.getTotalPrice(),
                    orderPrice,
                    saveInvoicce,
                    customerCoupon
            );

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

                BigDecimal orderPrice = orderPrice(savedOrderItems,null);
                TotalProcessDto totalProcessDto = processTotalPrice(savedOrderItems);
                BigDecimal totalTax = calculateTax(totalProcessDto.getSavedOrderItems());
                Invoice invoice = getInvoice(totalProcessDto.getTotalPrice(),totalTax,orderCreateDto);
                Invoice saveInvoicce = invoiceService.save(invoice);
                Order order = saveOrder(customer, orderCreateDto.getAddress(), savedOrderItems, orderStatus, totalProcessDto.getTotalPrice(), orderPrice, saveInvoicce,null);
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
                BigDecimal orderPrice = orderPrice(savedOrderItems,null);
                TotalProcessDto totalProcessDto = processTotalPrice(savedOrderItems);
                BigDecimal totalTax = calculateTax(totalProcessDto.getSavedOrderItems());
                Invoice invoice = getInvoice(totalProcessDto.getTotalPrice(),totalTax,orderCreateDto);
                Invoice saveInvoicce = invoiceService.save(invoice);
                Order order = saveOrder(guest, orderCreateDto.getAddress(), savedOrderItems, orderStatus, totalProcessDto.getTotalPrice(), orderPrice, saveInvoicce,null);
                return orderRepository.save(order);
            }
        }else
            throw new BadRequestException("Geçersiz Kullanıcı");
    }

    private BigDecimal calculateTax(Set<OrderItem> savedOrderItems) {
        BigDecimal totalTaxAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItem item : savedOrderItems) {
            BigDecimal price = item.getDiscountPrice();
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

    public BigDecimal orderPrice(Set<OrderItem> orderItems,Coupon coupon) {
        BigDecimal orderPrice = BigDecimal.valueOf(0);

        if (coupon != null) {
            if (coupon.getDiscountType().equals(Coupon.DiscountType.PERCENTAGE)) {
                BigDecimal discountValue = coupon.getDiscountValue(); // Örneğin %10 ise 10 gelir
                System.out.println("kupon 3");
                if (discountValue == null) {
                    throw new BadRequestException("İndirim yüzdesi belirtilmemiş!");
                }
                System.out.println("kupon 4");
                for (OrderItem orderItem : orderItems) {

                    if (coupon.getProductAssigned()){
                        boolean isProductInCoupon = coupon.getProducts().stream()
                                .anyMatch(product -> product.equals(orderItem.getProduct()));

                        if (isProductInCoupon) {
                            System.out.println("fiçeride");
                            BigDecimal divide = orderItem.getPrice().subtract(orderItem.getPrice().multiply(discountValue).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
                            orderPrice = orderPrice.add(divide);
                            System.out.println("orderprice: "+ orderPrice);
                            orderItem.setDiscountPrice(divide);
                        } else {
                            orderPrice = orderPrice.add(orderItem.getPrice());
                            orderItem.setDiscountPrice(orderItem.getPrice());
                        }
                    }else{
                        System.out.println("fiçeride");
                        BigDecimal divide = orderItem.getPrice().subtract(orderItem.getPrice().multiply(discountValue).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
                        orderPrice = orderPrice.add(divide);
                        System.out.println("orderprice: "+ orderPrice);
                        orderItem.setDiscountPrice(divide);
                    }
                }

                return orderPrice;

            } else if (coupon.getDiscountType().equals(Coupon.DiscountType.FIXEDAMOUNT)) {
                BigDecimal discountValue = coupon.getDiscountValue();

                if (discountValue == null) {
                    throw new BadRequestException("İndirim Miktarı belirtilmemiştir!");
                }

                if (coupon.getProductAssigned()) {
                    int orderItemSize = 0;
                    for(OrderItem orderItem : orderItems) {
                        if (coupon.getProducts().contains(orderItem.getProduct())) {
                            orderItemSize +=1;
                        }
                    }
                    BigDecimal substract = coupon.getDiscountValue()
                            .divide(BigDecimal.valueOf(orderItemSize), 2, RoundingMode.HALF_UP);

                    for (OrderItem orderItem: orderItems) {
                        if (coupon.getProducts().contains(orderItem.getProduct())) {
                            BigDecimal subtract = orderItem.getPrice().subtract(substract);
                            orderPrice = orderPrice.add(subtract);
                            orderItem.setDiscountPrice(subtract);
                        }else{
                            orderPrice = orderPrice.add(orderItem.getPrice());
                            orderItem.setDiscountPrice(orderItem.getPrice());
                        }
                    }
                }else{
                    int orderItemSize = orderItems.size();

                    BigDecimal substract = coupon.getDiscountValue()
                            .divide(BigDecimal.valueOf(orderItemSize), 2, RoundingMode.HALF_UP);

                    for (OrderItem orderItem: orderItems) {
                        BigDecimal subtract = orderItem.getPrice().subtract(substract);
                        orderPrice = orderPrice.add(subtract);
                        orderItem.setDiscountPrice(subtract);
                    }
                }

                return orderPrice;

            }else
                throw new BadRequestException("Geçersiz İndirim Tipi");
        }else{
            System.out.println("Kupon yok");
            for (OrderItem orderItem: orderItems) {
                orderPrice = orderPrice.add(orderItem.getPrice());
                orderItem.setDiscountPrice(orderItem.getPrice());
            }

            return orderPrice;
        }
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



    public Order saveOrder(User user, AddressOrderCreateDto addressOrderCreateDto, Set<OrderItem> orderItems, OrderStatus orderStatus, BigDecimal totalPrice, BigDecimal orderPrice,Invoice invoice,CustomerCoupon customerCoupon) {
        Order order = new Order(
                user,
                customerCoupon,
                addressOrderCreateDto.geliverId() != null ? addressOrderCreateDto.geliverId(): null,
                addressOrderCreateDto.firstName(),
                addressOrderCreateDto.lastName(),
                addressOrderCreateDto.username(),
                addressOrderCreateDto.countryName(),
                addressOrderCreateDto.countryIso(),
                addressOrderCreateDto.city(),
                addressOrderCreateDto.cityCode(),
                addressOrderCreateDto.district(),
                addressOrderCreateDto.districtId(),
                addressOrderCreateDto.addressLine1(),
                addressOrderCreateDto.postalCode(),
                addressOrderCreateDto.phoneNo(),
                orderItems,
                orderStatus,
                totalPrice,
                orderPrice,
                invoice);
        order.setPayments(null);
        order.setCity(addressOrderCreateDto.city());
        order.setDistrict(addressOrderCreateDto.district());
        order.setCountryName(addressOrderCreateDto.countryName());
        return order;

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
                            orderCreateDto.getInvoiceAddress().cityCode(),
                            orderCreateDto.getInvoiceAddress().district(),
                            orderCreateDto.getInvoiceAddress().districtId(),
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
                        orderCreateDto.getInvoiceAddress().city(),
                        orderCreateDto.getInvoiceAddress().cityCode(),
                        orderCreateDto.getInvoiceAddress().district(),
                        orderCreateDto.getInvoiceAddress().districtId(),
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
                            orderCreateDto.getInvoiceAddress().cityCode(),
                            orderCreateDto.getInvoiceAddress().district(),
                            orderCreateDto.getInvoiceAddress().districtId(),
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
                        orderCreateDto.getInvoiceAddress().city(),
                        orderCreateDto.getInvoiceAddress().cityCode(),
                        orderCreateDto.getInvoiceAddress().district(),
                        orderCreateDto.getInvoiceAddress().districtId(),
                        orderCreateDto.getAddress().addressLine1(),
                        orderCreateDto.getAddress().postalCode(),
                        orderCreateDto.getAddress().phoneNo());
            }
        }else
            throw new BadRequestException("Geçersiz Fatura Tipi");
    }





    private TotalProcessDto processTotalPrice(Set<OrderItem> savedOrderItems) {
        Merchant merchant = merchantService.getMerchant();
        BigDecimal kargoPrice = merchant.getShippingFee();
        System.out.println("******************** kargoprice : "+ kargoPrice);
        BigDecimal minPrice = merchant.getMinOrderAmount();
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        //Set<OrderItem> newOrderItems = new HashSet<>();
        for (OrderItem orderItem: savedOrderItems) {
            totalPrice = totalPrice.add(orderItem.getDiscountPrice());
        }
        if (totalPrice.compareTo(minPrice) < 0) {
            totalPrice = totalPrice.add(kargoPrice);
        }
        return new TotalProcessDto(totalPrice,savedOrderItems);
    }

    public void isCouponValidation(Coupon coupon,Set<OrderItem> savedOrderItems, Customer customer) {
        if (!coupon.getActive())
            throw new BadRequestException("Kullanılan Kupon Aktif değildir!");

        System.out.println("coupon.getTimesUsed():"+coupon.getTimesUsed());
        System.out.println("coupon.getTotalUsageLimit(): "+coupon.getTotalUsageLimit());
        if (coupon.getTimesUsed() >= coupon.getTotalUsageLimit())
            throw new BadRequestException("Kuponun Kullanım Limiti Dolmuştur");

        Instant now = Instant.now();

        if (coupon.getCouponStartDate() != null && now.isBefore(coupon.getCouponStartDate())) {
            throw new BadRequestException("Kupon henüz geçerli değildir!");
        }

        if (coupon.getCouponEndDate() != null && now.isAfter(coupon.getCouponEndDate())) {
            throw new BadRequestException("Kuponun geçerlilik süresi sona ermiştir!");
        }
        BigDecimal totalValue = BigDecimal.valueOf(0);
        System.out.println("TOTAL VALUE: "+totalValue);
        System.out.println("size: "+savedOrderItems.size());
        for (OrderItem orderItem: savedOrderItems) {
            System.out.println(orderItem.getPrice());
            totalValue = totalValue.add(orderItem.getPrice());
            System.out.println("TOTAL VALUE: "+totalValue);
        }
        if (totalValue.compareTo(coupon.getMinOrderAmountLimit()) < 0) {
            throw new BadRequestException("Sipariş tutarı kuponun minimum limiti olan " + coupon.getMinOrderAmountLimit() + " TL'den küçük.");
        }

        if (customer != null) {
            if (coupon.getCustomerAssigned()){
                if (!coupon.getCustomers().contains(customer)){
                    throw new BadRequestException("Kullanıcı bu kupona sahip değildir!");
                }
            }
        }

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
        return orderRepository.findAll(filterOrders(orderFilterRequest.getPaymentStatus(),orderFilterRequest.getStatusCode()),pageable).stream().map(orderBuilder::orderToOrderDetailDto).collect(Collectors.toList());
    }

    public List<Order> filterGuestSuccessOrder(User user){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Specification<Order> specification = filterGuestSuccessOrders(user);
        return orderRepository.findAll(specification,sort);
    }

    private Specification<Order> filterOrders(String status, OrderPackage.StatusCode statusCode) {
        if (!(status == null || status.isEmpty())){
            System.out.println("status: "+status);
            System.out.println("statusCode: "+statusCode);
            return Specification.where(hasStatus(OrderStatus.Status.valueOf(status)).and(hasStatusCode(statusCode)));

        }else{
            System.out.println("status: "+status);
            System.out.println("statusCode: "+statusCode);
            return Specification.where(hasStatus(null));

        }
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

    public List<Order> findSuccessOrderBetweenDates(Instant startDate, Instant endDate) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
        Specification<Order> where = Specification.where(hasStatus(OrderStatus.Status.APPROVED).and(hasDateBetween(startDate, endDate)));
        return orderRepository.findAll(where, sort);
    }

    public Specification<Order> hasStatus(OrderStatus.Status status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction(); // her şeyi döndür (filtre yok)
            }
            Join<Order, OrderStatus> statusJoin = root.join("orderStatus");
            return cb.equal(statusJoin.get("status"), status);
        };
    }
    public Specification<Order> hasStatusCode(OrderPackage.StatusCode statusCode) {
        return (root, query, cb) -> {
            Join<Order, OrderStatus> statusJoin = root.join("orderStatus", JoinType.LEFT);

            if (statusCode == null) {
                // orderPackages ilişkisi boş olanları getir
                return cb.isEmpty(statusJoin.get("orderPackages"));
            }

            // orderPackages içinde statusCode eşleşenleri getir
            Join<OrderStatus, OrderPackage> packageJoin = statusJoin.join("orderPackages", JoinType.LEFT);
            return cb.equal(packageJoin.get("statusCode"), statusCode);
        };
    }



    public Specification<Order> hasCargoStatus(OrderPackage.CargoStatus cargoStatus) {
        return (root, query, cb) -> {
            if (cargoStatus == null) {
                return cb.conjunction();
            }
            Join<Order, OrderStatus> statusJoin = root.join("orderStatus");
            Join<OrderStatus, OrderPackage> packageJoin = statusJoin.join("orderPackages");
            return cb.equal(packageJoin.get("cargoStatus"), cargoStatus);
        };
    }

    public Specification<Order> hasCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        return (root, query, cb) -> {
            if (cargoCompany == null) {
                return cb.conjunction();
            }
            Join<Order, OrderStatus> statusJoin = root.join("orderStatus");
            Join<OrderStatus, OrderPackage> packageJoin = statusJoin.join("orderPackages");
            return cb.equal(packageJoin.get("cargoCompany"), cargoCompany);
        };
    }



    public Specification<Order> hasUser(User user) {
        return (Root<Order> root,CriteriaQuery<?> query,CriteriaBuilder cb) ->
                user == null ? null : cb.equal(root.get("user"), user);
    }

    public List<OrderDetailDto> getAll() {
        return orderRepository.findAll().stream().map(orderBuilder::orderToOrderDetailDto).collect(Collectors.toList());
    }

    public Specification<Order> hasDateBetween(Instant startDate, Instant endDate) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }
            if (startDate != null && endDate != null) {
                return cb.between(root.get("createdAt"), startDate, endDate);
            }
            if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            }
            return cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
        };
    }


    public OrderDetailDto findOrderDetailByOrderCode(@NotNullParam String orderCode) {
        return orderBuilder.orderToOrderDetailDto(findByOrderCode(orderCode));
    }


    public CargoOfferResponsesUserDto cargoOffer(String orderCode, List<CargoOfferDesiRequestAdminDto> cargoOfferDesiRequestAdminDtos) {
        List<CargoOfferResponseDto> cargoOfferResponseDtos = new ArrayList<>();
        Order order = findByOrderCode(orderCode);
        String senderAddressId = merchantService.getMerchant().getDefaultSendingAddress().getGeliverId();

        if (senderAddressId == null){
            Merchant merchant = merchantService.getMerchant();
            AddressApiDto addressApiDto = new AddressApiDto(
                    merchant.getAddress().getFirstName()+ " " + merchant.getAddress().getLastName(),
                    merchant.getEmail(),
                    merchant.getPhoneNo(),
                    merchant.getAddress().getAddressLine1(),
                    "",
                    merchant.getAddress().getCountry().getIso(),
                    merchant.getAddress().getCity().getName(),
                    merchant.getAddress().getCity().getCityCode(),
                    merchant.getAddress().getDistrict().getName(),
                    merchant.getAddress().getDistrict().getDistrictId(),
                    merchant.getAddress().getPostalCode(),
                    false,
                    merchant.getAddress().getShortName()
            );
            shippingAddressService.createSendingAddress(addressApiDto);
        }
        String receiptAddress = order.getGeliverId();
        if (receiptAddress == null){
            AddressApiDto addressApiDto = buildAddressDto(order);
            try {
                receiptAddress = shippingAddressService.createReceivingAddress(addressApiDto).getId();
            } catch (JsonProcessingException e) {
                throw new BadRequestException("Müşteri adresi oluşturulamadı");
            }
        }

        System.out.println(9);
        List<CargoOfferRequestItem> cargoOfferRequestItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()){
            CargoOfferRequestItem cargoOfferRequestItem = new CargoOfferRequestItem(
                    orderItem.getProduct().getProductName(),
                    orderItem.getQuantity()
            );
            cargoOfferRequestItems.add(cargoOfferRequestItem);
        }

        CargoOfferRequestRecipientAddress recipientAddress = new CargoOfferRequestRecipientAddress(
                order.getFirstName() +" "+ order.getLastName(),
                order.getUsername(),
                order.getPhoneNumber(),
                order.getAddressLine1(),
                order.getCountryIso(),
                order.getCityCode(),
                order.getDistrict()
        );

        CargoOfferRequestOrder cargoOfferRequestOrder = new CargoOfferRequestOrder(
                "API",
                domain,
                order.getOrderCode(),
                order.getTotalPrice(),
                "TL"
        );


        if (!order.getOrderStatus().getOrderPackages().isEmpty()){
            order.getOrderStatus().getOrderPackages().clear();
        }

        for (CargoOfferDesiRequestAdminDto cargoOfferDesiRequestAdminDto: cargoOfferDesiRequestAdminDtos) {
            CargoOfferRequestDto cargoOfferRequestDto = new CargoOfferRequestDto(
                    true,
                    senderAddressId,
                    receiptAddress,
                    String.valueOf(cargoOfferDesiRequestAdminDto.getLength()),
                    String.valueOf(cargoOfferDesiRequestAdminDto.getHeight()),
                    String.valueOf(cargoOfferDesiRequestAdminDto.getWidth()),
                    cargoOfferDesiRequestAdminDto.getDistanceUnit(),
                    String.valueOf(cargoOfferDesiRequestAdminDto.getWeight()),
                    cargoOfferDesiRequestAdminDto.getMassUnit(),
                    cargoOfferRequestItems,
                    recipientAddress,
                    false,
                    cargoOfferRequestOrder
            );

            // 🚀 BURASI: Tüm değerleri tek tek yazdır
            System.out.println("===== CargoOfferRequestDto =====");
            System.out.println("test: " + cargoOfferRequestDto.isTest());
            System.out.println("senderAddressID: " + cargoOfferRequestDto.getSenderAddressID());
            System.out.println("returnAddressID: " + cargoOfferRequestDto.getReturnAddressID());
            System.out.println("length: " + cargoOfferRequestDto.getLength());
            System.out.println("height: " + cargoOfferRequestDto.getHeight());
            System.out.println("width: " + cargoOfferRequestDto.getWidth());
            System.out.println("distanceUnit: " + cargoOfferRequestDto.getDistanceUnit());
            System.out.println("weight: " + cargoOfferRequestDto.getWeight());
            System.out.println("massUnit: " + cargoOfferRequestDto.getMassUnit());
            System.out.println("recipientAddress.name: " + cargoOfferRequestDto.getRecipientAddress().getName());
            System.out.println("recipientAddress.email: " + cargoOfferRequestDto.getRecipientAddress().getEmail());
            System.out.println("recipientAddress.phone: " + cargoOfferRequestDto.getRecipientAddress().getPhone());
            System.out.println("recipientAddress.address1: " + cargoOfferRequestDto.getRecipientAddress().getAddress1());
            System.out.println("recipientAddress.countryCode: " + cargoOfferRequestDto.getRecipientAddress().getCountryCode());
            System.out.println("recipientAddress.countryCode: " + cargoOfferRequestDto.getRecipientAddress().getCountryCode());
            System.out.println("recipientAddress.cityCode: " + cargoOfferRequestDto.getRecipientAddress().getCityCode());
            System.out.println("recipientAddress.districtName: " + cargoOfferRequestDto.getRecipientAddress().getDistrictName());
            System.out.println("productPaymentOnDelivery: " + cargoOfferRequestDto.getProductPaymentOnDelivery());
            System.out.println("order.sourceCode: " + cargoOfferRequestDto.getOrder().getSourceCode());
            System.out.println("order.sourceIdentifier: " + cargoOfferRequestDto.getOrder().getSourceIdentifier());
            System.out.println("order.orderNumber: " + cargoOfferRequestDto.getOrder().getOrderNumber());
            System.out.println("order.totalAmount: " + cargoOfferRequestDto.getOrder().getTotalAmount());
            System.out.println("order.totalAmountCurrency: " + cargoOfferRequestDto.getOrder().getTotalAmountCurrency());
            System.out.println("items:");
            for (CargoOfferRequestItem item : cargoOfferRequestDto.getItems()) {
                System.out.println("  - title: " + item.getTitle() + ", quantity: " + item.getQuantity());
            }
            System.out.println("===== END =====");

            CargoOfferResponseDto createCargoOffers = shippingCargoService.getCreateCargoOffers(cargoOfferRequestDto);
            cargoOfferResponseDtos.add(createCargoOffers);
        }
        orderRepository.save(order);


        CargoOfferResponseDto cargoOfferResponseDto = cargoOfferResponseDtos.getFirst();
        return new CargoOfferResponsesUserDto(
                new CargoOfferResponseUserDto(
                        cargoOfferResponseDto.getData().getLength(),
                        cargoOfferResponseDto.getData().getWidth(),
                        cargoOfferResponseDto.getData().getHeight(),
                        cargoOfferResponseDto.getData().getWeight(),
                        cargoOfferResponseDto.getData().getDistanceUnit(),
                        cargoOfferResponseDto.getData().getMassUnit(),
                        new OfferUserDto(
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getId(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getCreatedAt(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getTotalAmount(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getDiscountRate(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getProviderCode(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getProviderServiceCode(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getAverageEstimatedTimeHumanReadible(),
                                cargoOfferResponseDto.getData().getOffers().getCheapest().getRating()

                        ),
                        new OfferUserDto(
                                cargoOfferResponseDto.getData().getOffers().getFastest().getId(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getCreatedAt(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getTotalAmount(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getDiscountRate(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getProviderCode(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getProviderServiceCode(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getAverageEstimatedTimeHumanReadible(),
                                cargoOfferResponseDto.getData().getOffers().getFastest().getRating()

                        ),
                        cargoOfferResponseDto.getData().getOffers().getList().stream().map(x->{
                            return new OfferUserDto(
                                    x.getId(),
                                    x.getCreatedAt(),
                                    x.getTotalAmount(),
                                    x.getDiscountRate(),
                                    x.getProviderCode(),
                                    x.getProviderServiceCode(),
                                    x.getAverageEstimatedTimeHumanReadible(),
                                    x.getRating()
                            );
                        }).toList()
                ));
    }


    public OfferApproveUserDto offerApprove(@NotNullParam String orderCode,@NotNullParam String offerId, @NotNullParam CargoOfferDesiRequestAdminDto cargoOfferDesiRequestAdminDto) {
        Order order = findByOrderCode(orderCode);
        OfferApproveDto offerApproveDto = shippingCargoService.offerApprove(offerId);
        ShipmentDto shipment = offerApproveDto.getData().getShipment();

        OrderPackage orderPackage = new OrderPackage(
                new HashSet<>(order.getOrderItems()),
                order.getFirstName()+ " " + order.getLastName(),
                cargoOfferDesiRequestAdminDto.getLength(),
                cargoOfferDesiRequestAdminDto.getWidth(),
                cargoOfferDesiRequestAdminDto.getHeight(),
                cargoOfferDesiRequestAdminDto.getWeight(),
                ProductTemplate.DistanceUnit.valueOf(cargoOfferDesiRequestAdminDto.getDistanceUnit()),
                ProductTemplate.MassUnit.valueOf(cargoOfferDesiRequestAdminDto.getMassUnit()),
                shipment.getId(),
                shipment.getResponsiveLabelURL(),
                OrderPackage.CargoCompany.valueOf(shipment.getProviderServiceCode()),
                OrderPackage.CargoStatus.information_received,
                shipment.getId(),
                shipment.getBarcode(),
                shipment.isProductPaymentOnDelivery(),
                offerApproveDto.getData().isCanceled(),
                offerApproveDto.getData().isRefund(),
                "Depo"
        );
        order.getOrderStatus().getOrderPackages().add(orderPackage);
        orderRepository.save(order);

        return new OfferApproveUserDto(
                new ShipmentUserDto(
                        offerApproveDto.getData().getShipment().getId(),
                        offerApproveDto.getData().getShipment().getCreatedAt(),
                        offerApproveDto.getData().getShipment().getUpdatedAt(),
                        offerApproveDto.getData().getShipment().isTest(),
                        offerApproveDto.getData().getShipment().getBarcode(),
                        offerApproveDto.getData().getShipment().getLabelFileType(),
                        offerApproveDto.getData().getShipment().getLabelURL(),
                        offerApproveDto.getData().getShipment().getStatusCode(),
                        offerApproveDto.getData().getShipment().getResponsiveLabelURL()
                )
        );
    }

    public OrderPackageResponseDto cargoManuel(String orderCode,OrderPackageRequestDto orderPackageRequestDto) {
        Order order = findByOrderCode(orderCode);
        List<OrderPackage> orderPackages = new ArrayList<>();

        OrderPackage orderPackage = new OrderPackage(
                new HashSet<>(order.getOrderItems()),
                orderPackageRequestDto.getPackageName(),
                orderPackageRequestDto.getLength(),
                orderPackageRequestDto.getWidth(),
                orderPackageRequestDto.getHeight(),
                orderPackageRequestDto.getWeight(),
                orderPackageRequestDto.getDistanceUnit(),
                orderPackageRequestDto.getMassUnit(),
                orderPackageRequestDto.getCargoId(),
                orderPackageRequestDto.getResponsiveLabelURL(),
                orderPackageRequestDto.getCargoCompany(),
                OrderPackage.CargoStatus.delivery_scheduled,
                orderPackageRequestDto.getCargoId(),
                orderPackageRequestDto.getBarcode(),
                false,
                false,
                false,
                "Alıcı Şubede"
        );

        OrderPackage saveOrderPackage = orderPackageService.createOrderPackage(orderPackage);
        orderPackages.add(saveOrderPackage);
        order.getOrderStatus().setOrderPackages(orderPackages);
        orderRepository.save(order);

        return new OrderPackageResponseDto(
                saveOrderPackage.getId(),
                saveOrderPackage.getOrderItems().stream().map(x->{
                    return new OrderItemResponseDto(
                            x.getProduct().getId(),
                            x.getProduct().getProductName(),
                            x.getQuantity(),
                            x.getProduct().getCoverImage().getUrl()
                    );
                }).collect(Collectors.toSet()),
                saveOrderPackage.getShipmentId(),
                saveOrderPackage.getStatusCode().name(),
                saveOrderPackage.getCargoId(),
                saveOrderPackage.getCargoCompany().name(),
                saveOrderPackage.getCargoStatus().getValue(),
                saveOrderPackage.getLocation(),
                Instant.now().atZone(ZoneId.of("Europe/Istanbul")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                false
        );
    }


    public String cargoCancel(Integer orderPackageId) {
        OrderPackage orderPackage = orderPackageService.findById(orderPackageId);
        OfferCancelDto offerCancelDto = shippingCargoService.offerCancel(orderPackage.getShipmentId());
        orderPackage.setUpdateAt(Instant.now());
        orderPackage.setCanceled(true);
        orderPackage.setCargoStatus(OrderPackage.CargoStatus.cancel);
        orderPackage.setStatusCode(OrderPackage.StatusCode.valueOf(offerCancelDto.getData().getStatusCode()));
        orderPackageService.save(orderPackage);
        return "Kargo iptal edildi";
    }

    private AddressApiDto buildAddressDto(Order order) {
        Random random = new Random();
        return new AddressApiDto(
                order.getFirstName() + " " + order.getLastName(),
                order.getUsername(),
                order.getPhoneNumber(),
                order.getAddressLine1(),
                "",
                order.getCountryIso(),
                order.getCity(),
                order.getCityCode(),
                order.getDistrict(),
                order.getDistrictID(),
                order.getPostalCode(),
                true,
                order.getFirstName() + " " + order.getLastName() + " " + (1000 + random.nextInt(9000))
        );
    }
}
