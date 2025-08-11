package com.example.ecommercebackend.service.payment;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.config.EncryptionUtils;
import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.refund.PaymentRefundCreateDto;
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
import com.example.ecommercebackend.entity.product.products.Product;
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
import com.iyzipay.model.Cancel;
import com.iyzipay.model.Refund;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
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
    private final PaymentFactory paymentFactory;

    @Value("${domain.name}")
    private String domainName;

    public PaymentService(OrderService orderService, PaymentRepository paymentRepository, SellService sellService, MailService mailService, CardService cardService, PaymentStrategy paymentStrategy, MerchantService merchantService, PaymentFactory paymentFactory) {
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
        this.sellService = sellService;
        this.mailService = mailService;
        this.cardService = cardService;
        this.paymentStrategy = paymentStrategy;
        this.merchantService = merchantService;
        this.paymentFactory = paymentFactory;
    }

    public String processIBAN(@NotNullParam OrderCreateDto orderCreateDto){
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
                Payment.PaymentMethod.IBAN,
                order,
                Payment.PaymentStatus.CHECKED
        );

        paymentRepository.save(payment);
        return "Sipariş kaydedildi, Onay işleminden itibaren kargoya verilecektir!";
    }

    public String checkedIBAN(String orderCode,Boolean check){
        Order order = orderService.findByOrderCode(orderCode);
        if(check){
            order.getPayments().setPaymentStatus(Payment.PaymentStatus.SUCCESS);
            orderService.save(order);
            return "Sipariş onaylandı!";
        } else{
            order.getPayments().setPaymentStatus(Payment.PaymentStatus.FAILED);
            orderService.save(order);
            return "Sipariş Reddedildi!";
        }
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
                Payment.PaymentMethod.CREDIT_CARD,
                order,
                Payment.PaymentStatus.PROCESS
        );
        System.out.println("payment 11");

        Payment savePayment = paymentRepository.save(payment);
        System.out.println("payment 12");


        order.setPayments(savePayment);
        System.out.println("payment 13");

        PaymentStrategy paymentStrategy = paymentFactory.getPaymentMethod(orderCreateDto.getPaymentCreditCardRequestDto().getPaymentMethod());
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
        BigDecimal finalTotalPrice = totalPrice;

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
        PaymentStrategy paymentStrategy = paymentFactory.getPaymentMethod("IYZICO");

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
            String redirectUrl = domainName+"/success-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            mailService.send(order.getUsername(),"Siparişiniz onaylandı",buildOrderConfirmationEmail(order));
            httpServletResponse.sendRedirect(redirectUrl);
        }else{
            payment.setPaymentStatus(Payment.PaymentStatus.FAILED);
            String redirectUrl = domainName+"/failed-payment?orderCode=" + payment.getOrder().getOrderCode(); // Query parametreli URL
            httpServletResponse.sendRedirect(redirectUrl);
        }
    }


    public BigDecimal maxRefund(@NotNullParam String orderCode){
        Order order = orderService.findByOrderCode(orderCode);
        return order.getTotalPrice().subtract(order.getRefundPrice());
    }


    /*
    %100 iade senaryosu

Parça iade (partial refund)

Fazla tutar talebi (geçersiz olmalı)

Kuruş farkı olan geçerli istek

Kuruş farkı toleransı aşan geçersiz istek
     */

    public String refund(PaymentRefundCreateDto orderItemRefund) {
        Order order = orderService.findByOrderCode(orderItemRefund.getOrderCode());

        if (!order.getPayments().getPaymentStatus().equals(Payment.PaymentStatus.SUCCESS))
            throw new BadRequestException("Bu sipariş üzerinde iade veya iptal işlemi zaten yapılmıştır!");

        BigDecimal tolerance = new BigDecimal("0.01");
        BigDecimal requestedRefund = orderItemRefund.getRefundAmount().setScale(2, RoundingMode.HALF_UP);

        // Toplam iade miktarının müşteri ödemesini aşmadığını kontrol et
        BigDecimal newTotalRefunded = order.getRefundPrice().add(requestedRefund).setScale(2, RoundingMode.HALF_UP);
        if (newTotalRefunded.compareTo(order.getTotalPrice()) > 0) {
            throw new BadRequestException("Toplam iade miktarı, Ödeme alınan tutarı aşamaz.");
        }

        Payment payment = order.getPayments();
        PaymentStrategy paymentStrategy = paymentFactory.getPaymentMethod("IYZICO");
        Refund refund = paymentStrategy.refund(payment.getPaymentId(), orderItemRefund.getRefundAmount());

        order.setRefundPrice(order.getRefundPrice().add(refund.getPrice()));

        BigDecimal difference = order.getTotalPrice().subtract(order.getRefundPrice()).abs();

        if (difference.compareTo(tolerance) <= 0) {
            // Tutar tamamen iade edilmiş ama ürünler eksik iade edilmiş

            order.getPayments().setPaymentStatus(Payment.PaymentStatus.REFUNDED);
        } else {
            // Hem ürün hem para kısmı eksik iade edilmiş
            order.getPayments().setPaymentStatus(Payment.PaymentStatus.PARTIAL_REFUNDED);
        }


        Set<Sell> refundSells = order.getRefundOrderItems().stream()
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

    public String cancel(String orderCode){
        Order order = orderService.findByOrderCode(orderCode);
        if (order.getPayments().getPaymentStatus().equals(Payment.PaymentStatus.SUCCESS)) {

            PaymentStrategy paymentStrategy = paymentFactory.getPaymentMethod("IYZICO");
            Cancel cancel = paymentStrategy.cancel(order.getPayments().getPaymentId());

            if (cancel != null) {
                if (cancel.getStatus().equals("success")){
                    order.getOrderStatus().setColor(OrderStatus.Color.RED);
                    order.getPayments().setPaymentStatus(Payment.PaymentStatus.CANCEL);

                    order.setRefundPrice(order.getCustomerPrice());
                    Order save = orderService.save(order);

                    Set<Sell> refundSells = save.getRefundOrderItems().stream()
                            .map(item -> sellService.saveCancel(item, new OrderItemTansactionId(
                                    String.valueOf(item.getId()),
                                    cancel.getPaymentId(),
                                    item.getPrice(),
                                    item.getDiscountPrice(),
                                    orderCode
                            )))
                            .collect(Collectors.toSet());

                    order.getPayments().setRefundSells(refundSells);
                    orderService.save(order);
                }
            }else
                throw new BadRequestException("İptal isteği 3. parti ödeme sistemi tarafından kabul edilmemiştir.");

            return "Sipariş iptali başarılıdır. " + cancel.getPrice();
        }else{
            throw new BadRequestException("Ödeme zaten başarısız!");
        }



    }

    private Payment findByConversationId(String conversationId) {
        return paymentRepository.findByConversationId(conversationId).orElseThrow(()-> new NotFoundException("Geçersiz Ödeme işlemi"));
    }


    public InstallmentInfoDto getBin(String binCode, BigDecimal price) {
        PaymentStrategy paymentStrategy = paymentFactory.getPaymentMethod("IYZICO");
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

    public String buildOrderConfirmationEmail(Order order) {
        String email = merchantService.getMerchant().getEmail();

        // Ürün satırlarını oluştur
        StringBuilder itemsRows = new StringBuilder();
        for (OrderItem item : order.getOrderItems()) {
            itemsRows.append("""
            <tr>
              <td style="padding:8px;border:1px solid #e9ecef;">%s x %s</td>
              <td style="padding:8px;border:1px solid #e9ecef;text-align:right;">₺%.2f</td>
            </tr>
            """.formatted(item.getQuantity(),
                    item.getProduct().getQuantity(),
                    item.getDiscountPrice()));
        }

        // Tarih formatı
        String orderDate = order.getCreatedAt()
                .atZone(java.time.ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", new java.util.Locale("tr", "TR")));

        // HTML şablon (yüzde işaretleri -> %%)
        return """
    <!doctype html>
    <html lang="tr">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width,initial-scale=1">
      <title>Sipariş Onayı</title>
    </head>
    <body style="margin:0;padding:0;background:#f4f6f8;font-family:Arial,Helvetica,sans-serif;">
      <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background:#f4f6f8;padding:24px 0;">
        <tr>
          <td align="center">
            <table role="presentation" width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;border-radius:10px;overflow:hidden;box-shadow:0 2px 10px rgba(0,0,0,0.05);">
              <!-- Header -->
              <tr>
                <td style="padding:20px 30px;background:linear-gradient(90deg,#0d6efd,#0069d9);color:#ffffff;">
                  <h1 style="margin:0;font-size:20px;">%s - Sipariş Onayı</h1>
                </td>
              </tr>

              <!-- Body -->
              <tr>
                <td style="padding:24px 30px;color:#333333;">
                  <p style="margin:0 0 12px 0;">Merhaba <strong>%s</strong>,</p>
                  <p style="margin:0 0 18px 0;">Ödemeniz başarıyla gerçekleşti. Aşağıda siparişinizin özetini bulabilirsiniz:</p>

                  <!-- Order summary card -->
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;margin-bottom:18px;">
                    <tr>
                      <td style="padding:12px;border:1px solid #e9ecef;border-radius:6px;background:#fbfcfd;">
                        <strong>Sipariş Kodu:</strong> %s<br>
                        <strong>Tutar:</strong> ₺%.2f<br>
                        <strong>Tarih:</strong> %s
                      </td>
                    </tr>
                  </table>

                  <!-- Items table -->
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;margin-bottom:18px;">
                    <thead>
                      <tr>
                        <th align="left" style="padding:8px;border:1px solid #e9ecef;background:#f8f9fb;">Ürün</th>
                        <th align="right" style="padding:8px;border:1px solid #e9ecef;background:#f8f9fb;">Fiyat</th>
                      </tr>
                    </thead>
                    <tbody>
                      %s
                      <tr>
                        <td style="padding:8px;border:1px solid #e9ecef;"><strong>Toplam</strong></td>
                        <td style="padding:8px;border:1px solid #e9ecef;text-align:right;"><strong>₺%.2f</strong></td>
                      </tr>
                    </tbody>
                  </table>
                  <p style="margin:0;color:#6c757d;font-size:13px;">
                    Eğer bu işlemle ilgili bir sorun varsa, lütfen bizimle iletişime geçin: <a href="mailto:%s">%s</a>
                  </p>
                </td>
              </tr>

              <!-- Footer -->
              <tr>
                <td style="padding:18px 30px;background:#f8f9fb;color:#888888;font-size:12px;text-align:center;">
                  © %d %s. Tüm hakları saklıdır.<br>
                  Bu e-posta otomatik oluşturulmuştur, lütfen cevaplamayın.
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(
                domainName,                      // Başlık
                order.getFirstName(),            // Kullanıcı adı
                order.getOrderCode(),            // Sipariş kodu
                order.getCustomerPrice(),        // Tutar
                orderDate,                       // Tarih
                itemsRows,                       // Ürün satırları
                order.getCustomerPrice(),        // Toplam fiyat
                email,                           // E-posta
                email,                           // E-posta
                java.time.Year.now().getValue(), // Yıl
                domainName                       // Firma adı
        );
    }


}
