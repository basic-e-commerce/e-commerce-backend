package com.example.ecommercebackend.service.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    @Bean
    public IMailStrategy TwilioStrategy() {
        return new TwilioStrategy();
    }
}
