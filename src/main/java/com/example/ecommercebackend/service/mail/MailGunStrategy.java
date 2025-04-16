package com.example.ecommercebackend.service.mail;

import io.github.cdimascio.dotenv.Dotenv;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

@Service
public class MailGunStrategy implements IMailStrategy{
    private String apiKey;

    public MailGunStrategy() {
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.apiKey = dotenv.get("MAILGUN_API_KEY");

    }

    @Override
    public String send(String from, String to, String subject, String body) {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/sandbox2cf166902f604b1faf1b778a78e47606.mailgun.org/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "Mailgun Sandbox <postmaster@sandbox2cf166902f604b1faf1b778a78e47606.mailgun.org>")
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("html", body)
                .asJson();
        System.out.println("body: ----------- "+request.getBody());
        return "mail";
    }
}
