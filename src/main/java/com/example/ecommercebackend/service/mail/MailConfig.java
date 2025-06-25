package com.example.ecommercebackend.service.mail;

import com.example.ecommercebackend.service.merchant.MerchantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    private final MerchantService merchantService;

    public MailConfig(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Bean
    public IMailStrategy TwilioStrategy() {
        return new TwilioStrategy(merchantService);
    }

    @Bean
    public IMailStrategy JavaMailStrategy() {
        return new JavaMailStrategy(merchantService);
    }
}
