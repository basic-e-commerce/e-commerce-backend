package com.example.ecommercebackend.service.mail;

public interface IMailStrategy {
    public String send(String from,String to,String subject, String body);
}
