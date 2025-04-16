package com.example.ecommercebackend.controller.redis;

import com.example.ecommercebackend.service.redis.RedisService;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {
    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping
    public String getKey(@RequestParam String key) {
        return (String) redisService.getData(key);
    }

    @PostMapping
    public String setKey(@RequestParam String key, @RequestParam String value) {
        redisService.saveData(key,value, Duration.ofMinutes(10));
        return "kaydedildi";
    }
}
