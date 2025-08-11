package com.example.ecommercebackend.service.mail;

import com.example.ecommercebackend.config.EncryptedStringConverter;
import com.example.ecommercebackend.config.EncryptionUtils;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.merchant.MerchantService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class JavaMailStrategy implements IMailStrategy {

    private static final Logger log = LoggerFactory.getLogger(JavaMailStrategy.class);
    private final MerchantService merchantService;

    public JavaMailStrategy(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Override
    public String send(String to, String subject, String body) {
        try {
            Merchant merchant = merchantService.getMerchant();
            JavaMailSender mailSender = createJavaMailSender(merchant);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(merchant.getEmail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = HTML format

            mailSender.send(mimeMessage);
            log.info("Email sent: "+ to);

            return "Mail Gönderildi!";
        }catch (Exception e) {
            throw new BadRequestException("Mail Gönderilemedi!"+e.getMessage());
        }
    }

    public JavaMailSender createJavaMailSender(Merchant merchant) {
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
