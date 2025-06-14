package com.example.ecommercebackend.service.payment;

import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.*;
import com.example.ecommercebackend.dto.product.order.OrderCreateDto;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.card.Card;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderStatus;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.payment.PaymentRepository;
import com.example.ecommercebackend.service.invoice.InvoiceService;
import com.example.ecommercebackend.service.mail.MailService;
import com.example.ecommercebackend.service.product.card.CardService;
import com.example.ecommercebackend.service.product.order.OrderService;
import com.example.ecommercebackend.service.product.products.SellService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class PaymentService {
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final SellService sellService;
    private final MailService mailService;
    private final InvoiceService invoiceService;
    private final CardService cardService;

    public PaymentService(OrderService orderService, PaymentRepository paymentRepository, SellService sellService, MailService mailService, InvoiceService invoiceService, CardService cardService) {
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
        this.sellService = sellService;
        this.mailService = mailService;
        this.invoiceService = invoiceService;
        this.cardService = cardService;
    }

    public String processCreditCardPayment(OrderCreateDto orderCreateDto, HttpServletRequest httpServletRequest) {
        Order order = orderService.createOrder(orderCreateDto);

        if (order.getPayments() != null){
            System.out.println("------------------------------");
            System.out.println(order.getPayments().stream().map(payment -> payment.getPaymentStatus().name()));
            System.out.println("------------------------------");

            if (order.getPayments().stream().anyMatch(payment -> payment.getPaymentStatus() == Payment.PaymentStatus.SUCCESS))
                throw new ResourceAlreadyExistException("payment is successful in order");

            if (order.getPayments().stream().anyMatch(payment -> payment.getPaymentStatus() != Payment.PaymentStatus.PROCESS))
                throw new ResourceAlreadyExistException("Geçerli bir sipariş bulunamadı");
        }

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
                BigDecimal.ONE,
                orderCreateDto.getPaymentCreditCardRequestDto().getInstallmentNumber(),
                order
        );
        Payment savePayment = paymentRepository.save(payment);
        order.getPayments().add(savePayment);

        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod(orderCreateDto.getPaymentCreditCardRequestDto().getPaymentMethod());
        BigDecimal totalPrice = order.getTotalPrice();
        String binNumber = orderCreateDto.getPaymentCreditCardRequestDto().getCreditCardRequestDto().getCardNumber().substring(0, 6);

        if (orderCreateDto.getPaymentCreditCardRequestDto().getInstallmentNumber() > 1){
            InstallmentInfoDto bin = getBin(binNumber, totalPrice);
            InstallmentPriceDto installmentPrice = getInstallmentPrice(binNumber, bin, orderCreateDto.getPaymentCreditCardRequestDto().getInstallmentNumber());
            totalPrice = installmentPrice.getTotalPrice();
            order.setTotalPrice(totalPrice);
            orderService.save(order);
        }

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

    public void payCallBack(Map<String, String> collections, HttpServletResponse httpServletResponse) throws IOException {
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentMethod("IYZICO");
        PayCallBackDto payCallBackDto = paymentStrategy.payCallBack(collections);

        if (payCallBackDto.getStatus().equals("success")) {
            Payment payment = findByConversationId(payCallBackDto.getConversationId());
            payment.setPaymentStatus(Payment.PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            // update orderstatus approved,green
            Order order = payment.getOrder();
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
                    card.setItems(new ArrayList<>());
                    cardService.save(card);
                }
            }
            // create save
            order.getOrderItems().forEach(sellService::save);
            String redirectUrl = "https://litysofttest1.site/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            //mailService.send(order.getUsername(),"Siparişiniz onaylandı","Order code: "+ order.getOrderCode());
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
