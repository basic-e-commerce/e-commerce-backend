package com.example.ecommercebackend.service.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final IMailStrategy mailStrategy;

    public MailService(@Qualifier("javaMailStrategy") IMailStrategy mailStrategy) {
        this.mailStrategy = mailStrategy;
    }

    public String send(String to, String subject, String body) {
        return mailStrategy.send(to, subject, body);
    }
}
