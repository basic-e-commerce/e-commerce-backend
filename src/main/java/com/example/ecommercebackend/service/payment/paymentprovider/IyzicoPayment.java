package com.example.ecommercebackend.service.payment.paymentprovider;


import com.example.ecommercebackend.builder.payment.PaymentBuilder;
import com.example.ecommercebackend.dto.payment.CreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.PaymentCreditCardRequestDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentInfoDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentPriceDto;
import com.example.ecommercebackend.dto.payment.response.PayCallBackDto;
import com.example.ecommercebackend.dto.payment.response.ProcessCreditCardDto;
import com.example.ecommercebackend.entity.product.category.Category;
import com.example.ecommercebackend.entity.product.order.Order;
import com.example.ecommercebackend.entity.product.order.OrderItem;
import com.example.ecommercebackend.exception.BadRequestException;
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
    private String apiKey = "sandbox-JbYzNd3TVSGRKgrKKFiM5Ha7MJP7YZSo";

    @Value("${payment.iyzico.secretKey}")
    private String apiSecret = "sandbox-mvXUSAUVAUhj7pNFFsbrKvWjGL5cEaUP";

    @Value("${payment.iyzico.baseUrl}")
    private String apiUrl = "https://sandbox-api.iyzipay.com";

    @Transactional
    @Retryable(
            value = {IOException.class, TimeoutException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000) // 2 saniye bekleyerek yeniden dene
    )  // Eğer ödeme sağlayıcısı geçici bir hata döndürürse (örneğin, zaman aşımı ya da bağlantı hatası), Spring otomatik olarak 3 defa yeniden deneyecek.
    @Override
    public ProcessCreditCardDto processCreditCardPayment(Order order, PaymentCreditCardRequestDto paymentCreditCardRequestDto, String conversationId, HttpServletRequest httpServletRequest) {

        System.out.println(7);
        Options options = getOptions();

        CreatePaymentRequest request = getCreatePaymentRequest(order,conversationId,paymentCreditCardRequestDto);
        System.out.println(8);

        PaymentCard paymentCard = getPaymentCard(paymentCreditCardRequestDto.getCreditCardRequestDto());
        request.setPaymentCard(paymentCard);
        System.out.println(9);

        Buyer buyer = getBuyer(order,httpServletRequest);
        request.setBuyer(buyer);
        System.out.println(10);

        Address shippingAddress = getShippingAddress(order);
        request.setShippingAddress(shippingAddress);
        System.out.println(11);

        Address billingAddress = getBillingAddress(order);
        request.setBillingAddress(billingAddress);
        System.out.println(12);

        List<BasketItem> basketItems = getBasketItems(order);
        request.setBasketItems(basketItems);
        System.out.println(13);

        ThreedsInitialize threedsInitialize = ThreedsInitialize.create(request, options);
        System.out.println(threedsInitialize);
        System.out.println(14);

        return new ProcessCreditCardDto(
                threedsInitialize.getConversationId(),
                threedsInitialize.getPaymentId(),
                order.getId(),
                threedsInitialize.getHtmlContent(),
                threedsInitialize.getStatus()
        );
    }


    @Override
    public PayCallBackDto payCallBack(Map<String, String> collections) {
        for (Map.Entry<String, String> entry : collections.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        String status = collections.get("status");
        String paymentId = collections.get("paymentId");
        String conversationId = collections.get("conversationId");
        String mdStatus = collections.get("mdStatus");
        System.out.println("mdstatus: "+mdStatus);

        if (!"success".equalsIgnoreCase(status)) {
            return new PayCallBackDto(
                    conversationId,
                    status
            );
        }else{
            System.out.println("Bir kısım başarılı");

            Options options = getOptions();

            CreateThreedsPaymentRequest request = new CreateThreedsPaymentRequest();
            request.setLocale(Locale.TR.getValue());
            request.setConversationId(conversationId);
            request.setPaymentId(paymentId);

            ThreedsPayment threedsPayment = ThreedsPayment.create(request,options);
            System.out.println("status: "+threedsPayment.getStatus());
            System.out.println("paymentId: "+threedsPayment.getPaymentId());
            System.out.println("conversationId: "+threedsPayment.getConversationId());

            if (threedsPayment.getStatus().equals("success")) {
                return new PayCallBackDto(
                        conversationId,
                        status
                );
            }else
                return new PayCallBackDto(
                        conversationId,
                        status
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
    public String refund(String paymentId, BigDecimal refundAmount) {
        Options options = getOptions();

        CreateRefundV2Request createRefundV2Request = new CreateRefundV2Request();
        createRefundV2Request.setPaymentId(paymentId);
        createRefundV2Request.setPrice(refundAmount);

        Refund refund = Refund.createV2(createRefundV2Request, options);
        System.out.println(refund.getPaymentId());

        return refund.getPaymentId();
    }


    public Options getOptions() {
        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(apiSecret);
        options.setBaseUrl(apiUrl);
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
        // komisyon cart curt vade farkı hesaplanmış ve postan geçecek olan miktar
        request.setPaidPrice(paidPrice);
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(paymentCreditCardRequestDto.getInstallmentNumber());
        request.setBasketId(order.getOrderCode());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl("https://litysofttest1.site/api/v1/payment/payCallBack");
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
        basketItem.setPrice(orderItem.getPrice());
        return basketItem;
    }
}
