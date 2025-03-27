package com.example.ecommercebackend.config.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexValidation {
    @Value("${regex.email}")
    public String emailRegex;
    @Value("${regex.password}")
    public String passwordRegex;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;


    public boolean isValidEmail(String email){

        String EMAIL_REGEX = emailRegex;
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = pattern.matcher(email);

        return emailMatcher.matches();
    }

    public boolean isValidPassword(String password){

        String PASSWORD_REGEX = passwordRegex;
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = pattern.matcher(password);

        return passwordMatcher.matches();
    }
}
