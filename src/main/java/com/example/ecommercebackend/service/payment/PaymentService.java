package com.example.ecommercebackend.service.payment;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.refund.RefundCreateDto;
import com.example.ecommercebackend.dto.payment.response.*;
import com.example.ecommercebackend.dto.product.order.OrderCreateDto;
import com.example.ecommercebackend.dto.product.order.OrderItemCreateDto;
import com.example.ecommercebackend.dto.product.orderitem.OrderItemRefundDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.payment.PaymentRepository;
import com.example.ecommercebackend.service.invoice.InvoiceService;
import com.example.ecommercebackend.service.mail.MailService;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.example.ecommercebackend.service.product.card.CardService;
import com.example.ecommercebackend.service.product.order.OrderService;
import com.example.ecommercebackend.service.product.products.SellService;
import com.iyzipay.model.Refund;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final SellService sellService;
    private final MailService mailService;
    private final CardService cardService;
    private final PaymentStrategy paymentStrategy;
    private final MerchantService merchantService;

    public PaymentService(OrderService orderService, PaymentRepository paymentRepository, SellService sellService, MailService mailService, CardService cardService, PaymentStrategy paymentStrategy, MerchantService merchantService) {
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
        this.sellService = sellService;
        this.mailService = mailService;
        this.cardService = cardService;
        this.paymentStrategy = paymentStrategy;
        this.merchantService = merchantService;
    }

    @Transactional
    public String processCreditCardPayment(@NotNullParam OrderCreateDto orderCreateDto,@NotNullParam HttpServletRequest httpServletRequest) {
        Order order = orderService.createOrder(orderCreateDto);

        System.out.println("payment 10");
        String conversationId = UUID.randomUUID().toString();
        Payment payment = new Payment(
                order.getFirstName(),
                order.getLastName(),
                order.getUsername(),
                order.getPhoneNumber(),
                order.getCountry(),
                order.getCity(),
                order.getPostalCode(),
                orderCreateDto.getPaymentCreditCardRequestDto().getCreditCardRequestDto().getCardHolderName(),
                conversationId,
                "İslem Baslatılıyor",
                BigDecimal.ZERO,
                orderCreateDto.getPaymentCreditCardRequestDto().getInstallmentNumber(),
                "transactionalId",
                order
        );
        System.out.println("payment 11");

        Payment savePayment = paymentRepository.save(payment);
        System.out.println("payment 12");


        order.setPayments(savePayment);
        System.out.println("payment 13");

        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(orderCreateDto.getPaymentCreditCardRequestDto().getPaymentMethod());
        System.out.println("payment 14");

        BigDecimal totalPrice = order.getTotalPrice();
        String binNumber = orderCreateDto.getPaymentCreditCardRequestDto().getCreditCardRequestDto().getCardNumber().substring(0, 6);
        System.out.println("payment 15");

        if (orderCreateDto.getPaymentCreditCardRequestDto().getInstallmentNumber() >= 1){
            InstallmentInfoDto bin = getBin(binNumber, totalPrice);
            InstallmentPriceDto installmentPrice = getInstallmentPrice(binNumber, bin, orderCreateDto.getPaymentCreditCardRequestDto().getInstallmentNumber());
            totalPrice = installmentPrice.getTotalPrice();
            order.setCustomerPrice(totalPrice);
            orderService.save(order);
        }
        System.out.println("payment 16");


        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println(18);
        BigDecimal finalTotalPrice = totalPrice;
        System.out.println("------------------ finalTotal price: "+finalTotalPrice);
        System.out.println(19);

        Future<ProcessCreditCardDto> future = executor.submit(() ->
                paymentStrategy.processCreditCardPayment(order, orderCreateDto.getPaymentCreditCardRequestDto(), conversationId,httpServletRequest)
        );

        try {
            ProcessCreditCardDto processCreditCardDto = future.get(10, TimeUnit.SECONDS);
            System.out.println(21);
            savePayment.setPaymentUniqId(processCreditCardDto.getPaymentId());
            payment.setTotalAmount(finalTotalPrice);
            paymentRepository.save(savePayment);
            System.out.println(22);

            if (processCreditCardDto.getConversationId().equals(conversationId) && processCreditCardDto.getStatus().equals("success")) {
                return processCreditCardDto.getGetHtmlContent();
            } else {
                throw new BadRequestException("Invalid request");
            }

        } catch (TimeoutException e) {
            future.cancel(true);
            throw new BadRequestException("Ödeme işlemi zaman aşımına uğradı.");
        } catch (ExecutionException e) {
            throw new BadRequestException("Ödeme işlemi sırasında hata oluştu: " + e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("İşlem kesildi.");
        } finally {
            executor.shutdown(); // Thread kapanıyor
        }

    }

    @Transactional
    public void payCallBack(Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("Gelen Callback Parametreleri:");
        collections.forEach((key, value) -> System.out.println(key + ": " + value));
        Payment payment = paymentRepository.findByConversationId(collections.get("conversationId")).orElseThrow(()-> new NotFoundException("Ödeme bulunamadı"));
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");

        PayCallBackDto payCallBackDto = paymentStrategy.payCallBack(
                new PaymentComplateDto(
                collections.get("status"),
                payment.getConversationId(),
                collections.get("paymentId"),
                payment.getOrder().getCustomerPrice(),
                payment.getOrder().getOrderCode(),
                "TRY"
        ));

        if (payCallBackDto.getStatus().equals("success")) {
            payment.setPaymentStatus(Payment.PaymentStatus.SUCCESS);
            payment.setPaymentId(payCallBackDto.getPaymentId());
            paymentRepository.save(payment);

            // update orderstatus approved,green
            Order order = payment.getOrder();
            if (order.getCustomerCoupon() != null) {
                order.getCustomerCoupon().setUsed(true);
                order.getCustomerCoupon().setUsedAt(Instant.now());
                order.getCustomerCoupon().getCoupon().setTimesUsed(order.getCustomerCoupon().getCoupon().getTimesUsed()+1);
            }
            Set<Sell> sells = new HashSet<>();

            for (OrderItem orderItem : order.getOrderItems()) {
                for (OrderItemTansactionId orderItemTansactionId: payCallBackDto.getOrderItemTansactionIds()){
                    if (Integer.valueOf(orderItemTansactionId.getOrderItemId()).equals(orderItem.getId())){
                        sells.add(sellService.save(orderItem,orderItemTansactionId));
                    }
                }
            }

            payment.setSells(sells);

            Invoice invoice = order.getInvoice();
            invoice.setPayment(payment);
            System.out.println("order firstname: "+order.getFirstName());

            OrderStatus orderStatus = order.getOrderStatus();
            System.out.println("order status: "+orderStatus.getStatus().name());
            orderStatus.setStatus(OrderStatus.Status.APPROVED);
            orderStatus.setColor(OrderStatus.Color.GREEN);
            orderService.updateOrderStatus(orderStatus);

            System.out.println("save order"+order.getFirstName());

            if (order.getUser() != null){
                if (order.getUser() instanceof Customer customer){
                    Card card = cardService.findByCustomer(customer);
                    card.getItems().clear();
                    card.setCoupon(null);
                    cardService.save(card);
                }
            }

            // create save
            String redirectUrl = "https://litysofttest1.site/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            //mailService.send(order.getUsername(),"Siparişiniz onaylandı","Order code: "+ order.getOrderCode());
            httpServletResponse.sendRedirect(redirectUrl);
        }else{
            payment.setPaymentStatus(Payment.PaymentStatus.FAILED);
            String redirectUrl = "https://litysofttest1.site/failed-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            httpServletResponse.sendRedirect(redirectUrl);
        }
    }


    public BigDecimal maxRefund(@NotNullParam String orderCode){
        Order order = orderService.findByOrderCode(orderCode);
        return order.getCustomerPrice().subtract(order.getRefundPrice());
    }


    /*
    %100 iade senaryosu

Parça iade (partial refund)

Fazla tutar talebi (geçersiz olmalı)

Kuruş farkı olan geçerli istek

Kuruş farkı toleransı aşan geçersiz istek
     */

    public String refund(RefundCreateDto orderItemRefund) {
        Order order = orderService.findByOrderCode(orderItemRefund.getOrderCode());
        Set<OrderItem> orderItems = order.getOrderItems();
        Set<OrderItem> refundItems = new HashSet<>();

        BigDecimal calculatedRefundAmount = BigDecimal.ZERO;

        for (OrderItemRefundDto refundDto : orderItemRefund.getOrderItemRefundDtos()) {

            Integer refundOrderItemId = refundDto.orderItemId();
            Integer refundProductId = refundDto.productId();



            System.out.println("İade talebi kontrol ediliyor -> OrderItemId: " + refundOrderItemId + ", ProductId: " + refundProductId);

            OrderItem matchingOrderItem = orderItems.stream()
                    .peek(orderItem -> {
                        System.out.println("Kontrol edilen OrderItem -> Id: " + orderItem.getId()
                                + ", ProductId: " + orderItem.getProduct().getId());
                    })
                    .filter(orderItem ->
                            orderItem.getId().equals(refundOrderItemId) &&
                                    orderItem.getProduct().getId() == refundProductId)
                    .findFirst()
                    .orElseThrow(() -> {
                        System.err.println("UYARI: Eşleşen OrderItem bulunamadı! İstenen -> OrderItemId: "
                                + refundOrderItemId + ", ProductId: " + refundProductId);
                        return new BadRequestException(
                                "Siparişte bu ürün kalemi bulunmamaktadır! OrderItemId: " +
                                        refundOrderItemId + ", ProductId: " + refundProductId
                        );
                    });

            System.out.println("Eşleşen OrderItem bulundu -> Id: " + matchingOrderItem.getId()
                    + ", ProductId: " + matchingOrderItem.getProduct().getId());








            BigDecimal unitPrice = matchingOrderItem.getPrice()
                    .divide(BigDecimal.valueOf(matchingOrderItem.getQuantity()), 2, RoundingMode.HALF_UP);

            BigDecimal unitDiscountPrice = matchingOrderItem.getDiscountPrice()
                    .divide(BigDecimal.valueOf(matchingOrderItem.getQuantity()), 2, RoundingMode.HALF_UP);

            BigDecimal refundPrice = unitPrice.multiply(BigDecimal.valueOf(refundDto.quantity())).setScale(2, RoundingMode.HALF_UP);
            BigDecimal refundDiscountPrice = unitDiscountPrice.multiply(BigDecimal.valueOf(refundDto.quantity())).setScale(2, RoundingMode.HALF_UP);
            calculatedRefundAmount = calculatedRefundAmount.add(refundDiscountPrice);


            OrderItem refundOrderItem = new OrderItem();
            refundOrderItem.setProduct(matchingOrderItem.getProduct());
            refundOrderItem.setQuantity(refundDto.quantity());
            refundOrderItem.setPrice(refundPrice);
            refundOrderItem.setDiscountPrice(refundDiscountPrice);
            refundOrderItem.setRefund(true);

            refundItems.add(refundOrderItem);
        }

        if (refundItems.isEmpty()) {
            throw new BadRequestException("İade Edilecek ürün bulunamamıştır");
        }

        // Kargo ücreti de iade edilecekse ekle
        if (orderItemRefund.getIncludeShippingFee()) {
            BigDecimal shippingPrice = order.getShippingFee() != null ? order.getShippingFee() : BigDecimal.ZERO;
            calculatedRefundAmount = calculatedRefundAmount.add(shippingPrice);
        }

        BigDecimal tolerance = new BigDecimal("0.01");
        BigDecimal requestedRefund = orderItemRefund.getRefundAmount().setScale(2, RoundingMode.HALF_UP);

        if (requestedRefund.subtract(calculatedRefundAmount).abs().compareTo(tolerance) > 0) {
            throw new BadRequestException("Talep edilen iade tutarı, ürünlerin iade edilebilir toplamından farklı.");
        }

        // Toplam iade miktarının müşteri ödemesini aşmadığını kontrol et
        BigDecimal newTotalRefunded = order.getRefundPrice().add(requestedRefund).setScale(2, RoundingMode.HALF_UP);
        if (newTotalRefunded.compareTo(order.getCustomerPrice()) > 0) {
            throw new BadRequestException("Toplam iade miktarı, müşterinin ödediği tutarı aşamaz.");
        }

        Payment payment = order.getPayments();
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");

        Refund refund = paymentStrategy.refund(payment.getPaymentId(), orderItemRefund.getRefundAmount());

        order.setRefundPrice(order.getRefundPrice().add(refund.getPrice()));
        if (order.getCustomerPrice().subtract(order.getRefundPrice()).abs().compareTo(tolerance) <= 0) {
            order.getOrderStatus().setStatus(OrderStatus.Status.REFUNDED);
            order.getOrderStatus().setColor(OrderStatus.Color.BLUE);
            order.getPayments().setPaymentStatus(Payment.PaymentStatus.REFUNDED);
        }else{
            order.getOrderStatus().setStatus(OrderStatus.Status.PARTIAL_REFUNDED);
            order.getOrderStatus().setColor(OrderStatus.Color.TURQUISE);
            order.getPayments().setPaymentStatus(Payment.PaymentStatus.PARTIAL_REFUNDED);

        }
        order.setRefundOrderItems(refundItems);
        Order savedOrder = orderService.save(order);

        Set<Sell> refundSells = savedOrder.getRefundOrderItems().stream()
                .map(item -> sellService.saveRefund(item, new OrderItemTansactionId(
                        String.valueOf(item.getId()),
                        refund.getPaymentTransactionId(),
                        item.getPrice(),
                        item.getDiscountPrice(),
                        orderItemRefund.getOrderCode()
                )))
                .collect(Collectors.toSet());

        order.getPayments().setRefundSells(refundSells);
        orderService.save(order);

        return "İade başarıyla yapıldı: " + orderItemRefund.getRefundAmount();
    }




//    public String refund(RefundCreateDto orderItemRefund){
//        Order order = orderService.findByOrderCode(orderItemRefund.getOrderCode());
//        Set<OrderItem> orderItems = order.getOrderItems();
//        Set<OrderItem> refundItems = new HashSet<>();
//
//
//        for (OrderItem orderItem : orderItems) {
//            OrderItem current = null;
//            OrderItemRefundDto currentRefundDto = null;
//            System.out.println("orderItem: "+ orderItem.getId());
//            for (OrderItemRefundDto orderItemRefundDto : orderItemRefund.getOrderItemRefundDtos()) {
//
//                System.out.println("orderItemRefundDtoproductId:"+orderItemRefundDto.productId());
//                System.out.println("orderItemproductId:"+orderItem.getProduct().getId());
//
//                if (orderItemRefundDto.orderItemId() == orderItem.getId() && orderItemRefundDto.productId() == orderItem.getProduct().getId()){
//                    System.out.println("idordrıtem:"+orderItem.getId());
//                    System.out.println("orderItemRefundDtoproductId:"+orderItemRefundDto.productId());
//                    System.out.println("orderItemproductId:"+orderItem.getProduct().getId());
//                    current = orderItem;
//                    currentRefundDto = orderItemRefundDto;
//
//                }
//            }
//            if (current != null){
//                BigDecimal eachPriceProductOrderItem = current.getPrice().divide(BigDecimal.valueOf(current.getQuantity()));
//                BigDecimal eachDiscountPriceProductOrderItem = current.getDiscountPrice().divide(BigDecimal.valueOf(current.getQuantity()));
//
//                OrderItem refundOrderItem = new OrderItem();
//                refundOrderItem.setProduct(current.getProduct());
//                refundOrderItem.setQuantity(currentRefundDto.quantity());
//                refundOrderItem.setPrice(eachPriceProductOrderItem.multiply(BigDecimal.valueOf(currentRefundDto.quantity())));
//                refundOrderItem.setDiscountPrice(eachDiscountPriceProductOrderItem.multiply(BigDecimal.valueOf(currentRefundDto.quantity())));
//                refundOrderItem.setRefund(true);
//
//                // Geçerli ise işleme ekle
//                refundItems.add(refundOrderItem);
//            }else{
//                throw new BadRequestException("Siparişte bu ürün kalemi bulunmamaktadır!");
//            }
//        }
//
//
//        if (!refundItems.isEmpty()){
//            BigDecimal maxRefundAmount = refundItems.stream()
//                    .map(OrderItem::getDiscountPrice)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            if (orderItemRefund.getRefundAmount().compareTo(maxRefundAmount) > 0) {
//                throw new BadRequestException("Talep edilen iade tutarı, izin verilen maksimum iade tutarını aşıyor!");
//            }
//
//            Payment payment = order.getPayments();
//            PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
//
//            Refund refund = paymentStrategy.refund(payment.getPaymentId(), orderItemRefund.getRefundAmount());
//            order.setRefundPrice(refund.getPrice());
//            order.setRefundOrderItems(refundItems);
//            order.getOrderStatus().setStatus(OrderStatus.Status.REFUNDED);
//            order.getOrderStatus().setColor(OrderStatus.Color.BLUE);
//            order.getPayments().setPaymentStatus(Payment.PaymentStatus.REFUNDED);
//            Order save = orderService.save(order);
//
//            Set<Sell> refundSells = save.getRefundOrderItems().stream().map(x-> {
//                return sellService.saveRefund(x,new OrderItemTansactionId(String.valueOf(x.getId()),refund.getPaymentTransactionId(),x.getPrice(),x.getDiscountPrice(),orderItemRefund.getOrderCode()));
//            }).collect(Collectors.toSet());
//            order.getPayments().setRefundSells(refundSells);
//            orderService.save(order);
//            return "İade başarıyla yapıldı: "+ orderItemRefund.getRefundAmount();
//
//        }else
//            throw new BadRequestException("İade Edilecek ürün bulunamamıştır");
//    }

    public void cancel(String orderCode){
        Order order = orderService.findByOrderCode(orderCode);



    }

    private Payment findByConversationId(String conversationId) {
        return paymentRepository.findByConversationId(conversationId).orElseThrow(()-> new NotFoundException("Geçersiz Ödeme işlemi"));
    }


    public InstallmentInfoDto getBin(String binCode, BigDecimal price) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        return paymentStrategy.getBin(binCode, price);
    }

    // Taksite göre fiyatı ayarlama
    private InstallmentPriceDto getInstallmentPrice(String binNumber, InstallmentInfoDto bin,int installmentNumber) {
        // Bin numarasına sahip InstallmentDetailDto'yu bul
        for (InstallmentDetailDto detail : bin.getInstallmentDetails()) {
            if (detail.getBinNumber().equals(binNumber) && detail.getInstallmentPrices() != null) {

                // İlgili taksit numarasına sahip InstallmentPriceDto'yu bul
                for (InstallmentPriceDto priceDto : detail.getInstallmentPrices()) {
                    if (priceDto.getInstallmentNumber() == installmentNumber) {
                        return priceDto;
                    }
                }
            }
        }
        throw new BadRequestException("Invalid Card");
    }

}
