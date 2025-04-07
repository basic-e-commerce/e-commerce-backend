package com.example.ecommercebackend.service.payment;

import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.*;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.product.products.Product;
import com.example.ecommercebackend.entity.product.products.Sell;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.payment.PaymentRepository;
import com.example.ecommercebackend.service.product.order.OrderService;
import com.example.ecommercebackend.service.product.products.SellService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class PaymentService {
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final SellService sellService;

    public PaymentService(OrderService orderService, PaymentRepository paymentRepository, SellService sellService) {
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
        this.sellService = sellService;
    }

    public String processCreditCardPayment(PaymentCreditCardRequestDto paymentCreditCardRequestDto, HttpServletRequest httpServletRequest) {
        Order order = orderService.findByOrderCode(paymentCreditCardRequestDto.getOrderCode());

        if (order.getPayments() != null){
            if (order.getPayments().stream().anyMatch(payment -> payment.getPaymentStatus() == Payment.PaymentStatus.SUCCESS))
                throw new ResourceAlreadyExistException("payment is successful in order");

            if (order.getPayments().stream().anyMatch(payment -> payment.getPaymentStatus() != Payment.PaymentStatus.PROCESS))
                throw new ResourceAlreadyExistException("Geçerli bir sipariş bulunamadı");
        }

        String conversationId = UUID.randomUUID().toString();
        Payment payment = new Payment(
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getName(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getSurname(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getEmail(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getPhone(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getIdentityNo(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getCountry(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getCity(),
                paymentCreditCardRequestDto.getOrderDeliveryRequestDto().getPostalCode(),
                paymentCreditCardRequestDto.getCreditCardRequestDto().getCardHolderName(),
                conversationId,
                "İslem Baslatılıyor",
                order
        );
        Payment savePayment = paymentRepository.save(payment);
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(paymentCreditCardRequestDto.getPaymentMethod());
        BigDecimal totalPrice = processTotalPrice(order.getTotalPrice());
        String binNumber = paymentCreditCardRequestDto.getCreditCardRequestDto().getCardNumber().substring(0, 6);



        if (paymentCreditCardRequestDto.getInstallmentNumber() > 1){
            InstallmentInfoDto bin = getBin(binNumber, totalPrice);
            InstallmentPriceDto installmentPrice = getInstallmentPrice(binNumber, bin, paymentCreditCardRequestDto.getInstallmentNumber());
            totalPrice = installmentPrice.getTotalPrice();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println(18);
        BigDecimal finalTotalPrice = totalPrice;
        System.out.println(19);
        Future<ProcessCreditCardDto> future = executor.submit(() ->
                paymentStrategy.processCreditCardPayment(finalTotalPrice, order, paymentCreditCardRequestDto, conversationId, httpServletRequest)
        );

        try {
            ProcessCreditCardDto processCreditCardDto = future.get(10, TimeUnit.SECONDS);
            System.out.println(21);
            savePayment.setPaymentUniqId(processCreditCardDto.getPaymentId());
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

    public void payCallBack(Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        PayCallBackDto payCallBackDto = paymentStrategy.payCallBack(collections);

        if (payCallBackDto.getStatus().equals("success")) {
            Payment payment = findByConversationId(payCallBackDto.getConversationId());
            payment.setPaymentStatus(Payment.PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            // update orderstatus approved,green
            Order order = orderService.findByPayment(payment);
            OrderStatus orderStatus = order.getOrderStatus();
            orderStatus.setStatus(OrderStatus.Status.APPROVED);
            orderStatus.setColor(OrderStatus.Color.GREEN);
            OrderStatus saveOrderStatus = orderService.updateOrderStatus(orderStatus);

            order.setOrderStatus(saveOrderStatus);
            Order saveOrder = orderService.save(order);

            // create save
            order.getOrderItems().forEach(sellService::save);


            saveOrder.getOrderItems().forEach(orderItem -> {
                Product product = orderItem.getProduct();
                int productQuantity = orderItem.getQuantity();

                product.setQuantity(productQuantity-orderItem.getQuantity());

                orderService.saveProduct(product);
            });


            String redirectUrl = "https://litysofttest1.site/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            httpServletResponse.sendRedirect(redirectUrl);
        }else{
            Payment payment = findByConversationId(payCallBackDto.getConversationId());
            payment.setPaymentStatus(Payment.PaymentStatus.FAILED);
            String redirectUrl = "https://litysofttest1.site/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            httpServletResponse.sendRedirect(redirectUrl);
        }
    }

    private Payment findByConversationId(String conversationId) {
        return paymentRepository.findByConversationId(conversationId).orElseThrow(()-> new NotFoundException("Geçersiz Ödeme işlemi"));
    }


    public InstallmentInfoDto getBin(String binCode, BigDecimal price) {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        return paymentStrategy.getBin(binCode, price);
    }

    private BigDecimal processTotalPrice(BigDecimal totalPrice) {
        BigDecimal kargoPrice = new  BigDecimal("75.00");
        BigDecimal minPrice = new BigDecimal("2000.00");

        if (totalPrice.compareTo(minPrice) < 0) {
            totalPrice.add(kargoPrice);
        }
        return totalPrice;
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
