package com.example.ecommercebackend.service.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Twilio {
    private String apiKey;
    public Twilio() {
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.apiKey = dotenv.get("SENDGRID_API_KEY");
    }

    public String send() {

        Email from = new Email("fatihgs133@gmail.com");
        String subject = "Haktana bastığım e-posta";
        Email to = new Email("hktndmrr25@gmail.com");
        Content content = new Content("text/plain", "Haktana bastığımın kanıt maili");
        Mail mail = new Mail(from, subject, to, content);

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
