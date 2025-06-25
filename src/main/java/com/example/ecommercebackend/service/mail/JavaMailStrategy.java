package com.example.ecommercebackend.service.mail;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.merchant.MerchantService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class JavaMailStrategy implements IMailStrategy {

    private final MerchantService merchantService;

    public JavaMailStrategy(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Override
    public String send(String from, String to, String subject, String body) {
        try {
            JavaMailSender mailSender = createJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

            return "Mail Gönderildi!";
        }catch (Exception e) {
            throw new BadRequestException("Mail Gönderilemedi!");
        }
    }

    public JavaMailSender createJavaMailSender() {
        Merchant merchant = merchantService.getMerchant();
        String email = merchant.getEmail();
        String mailPassword = merchant.getEmailPassword();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "false");


        return mailSender;
    }
}
