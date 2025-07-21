package com.example.ecommercebackend.service.mail;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.merchant.MerchantService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class JavaMailStrategy implements IMailStrategy {

    private final MerchantService merchantService;

    public JavaMailStrategy(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Override
    public String send(String to, String subject, String body) {
        try {
            Merchant merchant = merchantService.getMerchant();

            JavaMailSender mailSender = createJavaMailSender(merchant);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(merchant.getEmail());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");

            return "Mail Gönderildi!";
        }catch (Exception e) {
            throw new BadRequestException("Mail Gönderilemedi!"+e.getMessage());
        }
    }

    public JavaMailSender createJavaMailSender(Merchant merchant) {
        String email = merchant.getEmail();
        System.out.println("gönderici:"+email);
        String mailPassword = merchant.getEmailPassword();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(mailPassword);
        System.out.println("-------11111");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "false");


        return mailSender;
    }
}
