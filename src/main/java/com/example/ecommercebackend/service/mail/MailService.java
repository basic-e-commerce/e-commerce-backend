package com.example.ecommercebackend.service.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mail.from}")
    private String from;

    private final IMailStrategy mailStrategy;

    public MailService(@Qualifier("twilioStrategy") IMailStrategy mailStrategy) {
        this.mailStrategy = mailStrategy;
    }
    public String send(String to, String subject, String body) {
        return mailStrategy.send(from, to, subject, body);
    }
}
