package com.example.ecommercebackend.service.mail;

public interface IMailStrategy {
    public String send(String to,String subject, String body);
}
