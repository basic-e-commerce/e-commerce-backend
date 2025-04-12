package com.example.ecommercebackend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnviromentConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory(System.getProperty("user.dir"))  // Projenin ana dizinini kullanır
                .filename(".env")
                .load();
    }
}
