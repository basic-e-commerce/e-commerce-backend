package com.example.ecommercebackend.service.mail;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TwilioStrategy implements IMailStrategy{

    private String apiKey;
    private final MerchantService merchantService;
    public TwilioStrategy(MerchantService merchantService) {
        this.merchantService = merchantService;
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.apiKey = dotenv.get("SENDGRID_API_KEY");

    }

    @Override
    public String send(String to,String subject,String body) {
        Merchant merchant = merchantService.getMerchant();
        Email from1 = new Email(merchant.getEmail());
        Email to1 = new Email(to);
        Content content = new Content("text/html", body);
        Mail mail = new Mail(from1, subject, to1, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw new RuntimeException("hata mesajı twilio: "+ex.getMessage());
        }

        return "Mail gönderildi";
    }
}
