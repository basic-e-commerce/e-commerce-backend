package com.example.ecommercebackend.controller.mail;

import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.service.mail.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    @RateLimit(limit = 2, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> send(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        return new ResponseEntity<>(mailService.send(to, subject, body), HttpStatus.CREATED);
    }
}
