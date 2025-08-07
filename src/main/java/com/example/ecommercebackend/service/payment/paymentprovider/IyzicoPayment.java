package com.example.ecommercebackend.service.payment.paymentprovider;


import com.example.ecommercebackend.builder.payment.PaymentBuilder;
import com.example.ecommercebackend.dto.payment.CreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.*;
import com.example.ecommercebackend.entity.payment.Payment;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.payment.PaymentService;
import com.example.ecommercebackend.service.payment.PaymentStrategy;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.model.Currency;
import com.iyzipay.model.Locale;
import com.iyzipay.request.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class IyzicoPayment implements PaymentStrategy {

    @Value("${payment.iyzico.apiKey}")
    private String apiKey;

    @Value("${payment.iyzico.secretKey}")
    private String iyzicoApiSecret;

    @Value("${payment.iyzico.baseUrl}")
    private String iyzicoApiUrl;

    @Value("${domain.name}")
    private String domainName;

    @Transactional
    @Retryable(
            value = {IOException.class, TimeoutException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000) // 2 saniye bekleyerek yeniden dene
    )  // Eğer ödeme sağlayıcısı geçici bir hata döndürürse (örneğin, zaman aşımı ya da bağlantı hatası), Spring otomatik olarak 3 defa yeniden deneyecek.
    @Override
    public ProcessCreditCardDto processCreditCardPayment(Order order, PaymentCreditCardRequestDto paymentCreditCardRequestDto, String conversationId, HttpServletRequest httpServletRequest) {

        Options options = getOptions();
        CreatePaymentRequest request = getCreatePaymentRequest(order,conversationId,paymentCreditCardRequestDto);

        PaymentCard paymentCard = getPaymentCard(paymentCreditCardRequestDto.getCreditCardRequestDto());
        request.setPaymentCard(paymentCard);


        Buyer buyer = getBuyer(order,httpServletRequest);
        request.setBuyer(buyer);


        Address shippingAddress = getShippingAddress(order);
        request.setShippingAddress(shippingAddress);


        Address billingAddress = getBillingAddress(order);
        request.setBillingAddress(billingAddress);


        List<BasketItem> basketItems = getBasketItems(order);
        request.setBasketItems(basketItems);


        ThreedsInitialize threedsInitialize = ThreedsInitialize.create(request, options);
        System.out.println(threedsInitialize);


        return new ProcessCreditCardDto(
                threedsInitialize.getConversationId(),
                threedsInitialize.getPaymentId(),
                order.getId(),
                threedsInitialize.getHtmlContent(),
                threedsInitialize.getStatus()
        );
    }


    @Override
    public PayCallBackDto payCallBack(PaymentComplateDto paymentComplateDto) {

        if (!"success".equalsIgnoreCase(paymentComplateDto.getStatus())) {
            return new PayCallBackDto(
                    "Failed",
                    paymentComplateDto.getConversationId(),
                    paymentComplateDto.getStatus(),
                    null
            );
        }else{
            System.out.println("Bir kısım başarılı");

            Options options = getOptions();

            CreateThreedsPaymentRequestV2 createThreedsPaymentRequestV2 = new CreateThreedsPaymentRequestV2();
            createThreedsPaymentRequestV2.setPaymentId(paymentComplateDto.getPaymentId());
            createThreedsPaymentRequestV2.setConversationId(paymentComplateDto.getConversationId());
            createThreedsPaymentRequestV2.setPaidPrice(paymentComplateDto.getPaidPrice());
            createThreedsPaymentRequestV2.setCurrency(paymentComplateDto.getCurrency());
            createThreedsPaymentRequestV2.setBasketId(paymentComplateDto.getBasketId());
            createThreedsPaymentRequestV2.setLocale(Locale.TR.getValue());

            ThreedsPayment threedsPayment =ThreedsPayment.createV2(createThreedsPaymentRequestV2, options);
            System.out.println(threedsPayment.getStatus());
            System.out.println("errorMessage: "+threedsPayment.getErrorMessage());
            System.out.println("errorCode: "+threedsPayment.getErrorCode());


//            CreateThreedsPaymentRequest request = new CreateThreedsPaymentRequest();
//            request.setLocale(Locale.TR.getValue());
//            request.setConversationId(conversationId);
//            request.setPaymentId(paymentId);
//
//            ThreedsPayment threedsPayment = ThreedsPayment.create(request,options);


            System.out.println("status: " + threedsPayment.getStatus());
            System.out.println("price: " + threedsPayment.getPrice());
            System.out.println("paidPrice: " + threedsPayment.getPaidPrice());
            System.out.println("currency: " + threedsPayment.getCurrency());
            System.out.println("installment: " + threedsPayment.getInstallment());
            System.out.println("paymentId: " + threedsPayment.getPaymentId());
            System.out.println("paymentStatus: " + threedsPayment.getPaymentStatus());
            System.out.println("fraudStatus: " + threedsPayment.getFraudStatus());
            System.out.println("merchantCommissionRate: " + threedsPayment.getMerchantCommissionRate());
            System.out.println("merchantCommissionRateAmount: " + threedsPayment.getMerchantCommissionRateAmount());
            System.out.println("iyziCommissionRateAmount: " + threedsPayment.getIyziCommissionRateAmount());
            System.out.println("iyziCommissionFee: " + threedsPayment.getIyziCommissionFee());
            System.out.println("cardType: " + threedsPayment.getCardType());
            System.out.println("cardAssociation: " + threedsPayment.getCardAssociation());
            System.out.println("cardFamily: " + threedsPayment.getCardFamily());
            System.out.println("cardToken: " + threedsPayment.getCardToken());
            System.out.println("cardUserKey: " + threedsPayment.getCardUserKey());
            System.out.println("binNumber: " + threedsPayment.getBinNumber());
            System.out.println("basketId: " + threedsPayment.getBasketId());
            System.out.println("authCode: " + threedsPayment.getAuthCode());
            System.out.println("phase: " + threedsPayment.getPhase());
            System.out.println("lastFourDigits: " + threedsPayment.getLastFourDigits());
            System.out.println("posOrderId: " + threedsPayment.getPosOrderId());
            System.out.println("hostReference: " + threedsPayment.getHostReference());
            System.out.println("iban: " + threedsPayment.getIban());
            System.out.println("legalCompanyTitle: " + threedsPayment.getLegalCompanyTitle());
            System.out.println("bankName: " + threedsPayment.getBankName());
            System.out.println("referenceCode: " + threedsPayment.getReferenceCode());
            System.out.println("mdStatus: " + threedsPayment.getMdStatus());
            System.out.println("signature: " + threedsPayment.getSignature());

            if (threedsPayment.getPaymentItems() != null && !threedsPayment.getPaymentItems().isEmpty()) {
                System.out.println("Payment Items:");
                for (PaymentItem item : threedsPayment.getPaymentItems()) {
                    System.out.println("\tItemId: " + item.getItemId());
                    System.out.println("\tPaymentTransactionId: " + item.getPaymentTransactionId());
                    System.out.println("\tTransactionStatus: " + item.getTransactionStatus());
                    System.out.println("\tPrice: " + item.getPrice());
                    System.out.println("\tPaidPrice: " + item.getPaidPrice());
                    System.out.println("--------");
                }
            }

            if (threedsPayment.getStatus().equals("success")) {
                return new PayCallBackDto(
                        threedsPayment.getPaymentId(),
                        threedsPayment.getConversationId(),
                        threedsPayment.getStatus(),
                        threedsPayment.getPaymentItems().stream().map(x->{
                            return new OrderItemTansactionId(
                                    x.getItemId(),
                                    x.getPaymentTransactionId(),
                                    x.getPrice(),
                                    x.getPaidPrice(),
                                    threedsPayment.getBasketId()
                            );
                        }).toList()
                );
            }else
                return new PayCallBackDto(
                        threedsPayment.getPaymentId(),
                        threedsPayment.getConversationId(),
                        threedsPayment.getStatus(),
                        new ArrayList<>()
                );
        }

    }

    @Override
    public InstallmentInfoDto getBin(String bin, BigDecimal price) {
        RetrieveBinNumberRequest retrieveBinNumberRequest = new RetrieveBinNumberRequest();
        retrieveBinNumberRequest.setBinNumber(bin);

        Options options = getOptions();
        BinNumber binNumber = BinNumber.retrieve(retrieveBinNumberRequest, options);

        if (binNumber.getCardType().equals("CREDIT_CARD")){

            RetrieveInstallmentInfoRequest retrieveInstallmentInfoRequest = new RetrieveInstallmentInfoRequest();
            retrieveInstallmentInfoRequest.setBinNumber(bin);
            retrieveInstallmentInfoRequest.setPrice(price);
            retrieveInstallmentInfoRequest.setCurrency(Currency.TRY.name());

            InstallmentInfo installmentInfo = InstallmentInfo.retrieve(retrieveInstallmentInfoRequest,options);
            System.out.println("installmentInfo: "+installmentInfo);

            return PaymentBuilder.installmentInfoDto(installmentInfo);
        }else
            throw new BadRequestException("Geçersiz Kart");

    }

    @Override
    public Refund refund(String paymentId, BigDecimal refundAmount) {
        Options options = getOptions();

        CreateRefundV2Request createRefundV2Request = new CreateRefundV2Request();
        createRefundV2Request.setPaymentId(paymentId);
        createRefundV2Request.setPrice(refundAmount);
        createRefundV2Request.setConversationId(UUID.randomUUID().toString());

        Refund refund = Refund.createV2(createRefundV2Request, options);

        if (refund.getStatus().equals("success")) {
            if (!refund.getConversationId().equals(createRefundV2Request.getConversationId())) {
                throw new BadRequestException("Yapılan istekte hata vardır!");
            }
            return refund;
        } else if (refund.getStatus().equals("failure")) {
            throw new BadRequestException("İade yapılamadı:"+refund.getErrorMessage());
        }else{
            throw new BadRequestException("Bilinmeyen hata!");
        }

    }

    @Override
    public Cancel cancel(String paymentId){
        Options options = getOptions();
        String uuid = UUID.randomUUID().toString();

        CreateCancelRequest request = new CreateCancelRequest();
        request.setPaymentId(paymentId);
        request.setConversationId(uuid);

        Cancel cancel = Cancel.create(request, options);

        if (cancel.getStatus().equals("success")) {
            if (!cancel.getConversationId().equals(uuid)) {
                throw new BadRequestException("Ödeme tutarsızlığı bulunmaktadır!");
            }
        }else if (cancel.getStatus().equals("failure")) {
            throw new BadRequestException("İptal işlemi başarısız! "+cancel.getErrorMessage());
        }else
            throw new BadRequestException("Bilinmeyen hata!");
        return cancel;
    }


    public Options getOptions() {
        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(iyzicoApiSecret);
        options.setBaseUrl(iyzicoApiUrl);
        return options;
    }

    public CreatePaymentRequest getCreatePaymentRequest(Order order,String conversationId,PaymentCreditCardRequestDto paymentCreditCardRequestDto) {
        CreatePaymentRequest request = new CreatePaymentRequest();
        BigDecimal paidPrice = order.getCustomerPrice();

        System.out.println("------------------------- last paid price: "+paidPrice);

        request.setLocale(Locale.TR.getValue());
        request.setConversationId(conversationId);
        // sepette ürün fiyatları toplamı bu olmalı
        request.setPrice(order.getPrice());
        System.out.println("--------------createPaymentRequest : "+ order.getPrice());
        // komisyon cart curt vade farkı hesaplanmış ve postan geçecek olan miktar
        request.setPaidPrice(paidPrice);
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(paymentCreditCardRequestDto.getInstallmentNumber());
        request.setBasketId(order.getOrderCode());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl(domainName+"/api/v1/payment/payCallBack");
        return request;
    }

    public PaymentCard getPaymentCard(CreditCardRequestDto creditCardRequestDto) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(creditCardRequestDto.getCardHolderName());
        paymentCard.setCardNumber(creditCardRequestDto.getCardNumber());
        paymentCard.setExpireMonth(creditCardRequestDto.getExpirationMonth());
        paymentCard.setExpireYear(creditCardRequestDto.getExpirationYear());
        paymentCard.setCvc(creditCardRequestDto.getCvv());
        paymentCard.setRegisterCard(0);

        return paymentCard;
    }

    public Buyer getBuyer(Order order, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
            System.out.println("------ ip: "+ip);
        }else
            ip="84.17.86.74";

        String username;
        if (order.getUser() != null){
            username = order.getUser().getUsername();
        }else
            username = order.getUsername();

        Buyer buyer = new Buyer();
        buyer.setId(order.getOrderCode());
        buyer.setName(order.getFirstName());
        buyer.setSurname(order.getLastName());
        buyer.setGsmNumber(order.getPhoneNumber());
        buyer.setEmail(username);
        buyer.setIdentityNumber("11111111111");
        buyer.setRegistrationAddress(order.getAddressLine1());
        buyer.setIp(ip);
        buyer.setCity(order.getCity());
        buyer.setCountry(order.getCountry());
        buyer.setZipCode(order.getPostalCode());

        return buyer;
    }

    public Address getShippingAddress(Order order) {
        Address shippingAddress = new Address();
        shippingAddress.setContactName(order.getFirstName() + " " + order.getLastName());
        shippingAddress.setCity(order.getCity());
        shippingAddress.setCountry(order.getCountry());
        shippingAddress.setAddress(order.getAddressLine1());
        shippingAddress.setZipCode(order.getPostalCode());
        return shippingAddress;
    }

    public Address getBillingAddress(Order order) {
        Address billingAddress = new Address();
        String constactName = order.getFirstName() + " " + order.getLastName();
        String city;
        String country;
        String address;
        String zip;

        if (order.getInvoice() != null){
            city = order.getInvoice().getCity();
            country = order.getInvoice().getCountryName();
            address = order.getInvoice().getAddressLine1();
            zip = order.getInvoice().getPostalCode();
        }else {
            city = order.getCity();
            country = order.getCountry();
            address = order.getAddressLine1();
            zip = order.getPostalCode();
        }

        billingAddress.setContactName(constactName);
        billingAddress.setCity(city);
        billingAddress.setCountry(country);
        billingAddress.setAddress(address);
        billingAddress.setZipCode(zip);
        return billingAddress;
    }

    private List<BasketItem> getBasketItems(Order order) {

        List<BasketItem> basketItems = new ArrayList<>();
        for (OrderItem orderItems : order.getOrderItems()) {
            BasketItem basketItem = getBasketItem(orderItems);
            basketItems.add(basketItem);
        }
        return basketItems;
    }

    private BasketItem getBasketItem(OrderItem orderItem) {
        BasketItem basketItem = new BasketItem();
        basketItem.setId(String.valueOf(orderItem.getId()));
        basketItem.setName(orderItem.getProduct().getProductName());
        basketItem.setCategory1("categoryName");
        basketItem.setItemType(BasketItemType.PHYSICAL.name());
        basketItem.setPrice(orderItem.getDiscountPrice());
        System.out.println("\n\nbasketItem discountPrice: "+ orderItem.getDiscountPrice());
        return basketItem;
    }
}
