package com.example.ecommercebackend.controller.mail;

import com.example.ecommercebackend.service.mail.Twilio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
public class TwilioController {
    private final Twilio twilio;

    public TwilioController(Twilio twilio) {
        this.twilio = twilio;
    }

    @GetMapping
    public ResponseEntity<String> send(){
        return ResponseEntity.ok(twilio.send());
    }
}
