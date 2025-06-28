package com.example.ecommercebackend.service.redis;

import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private final RedisService redisService;

    public RateLimiterService(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * Rate limit kontrolü yapar.
     * @param ip       Client IP
     * @param method   HTTP Method (GET, POST vs.)
     * @param endpoint URI
     * @param limit    Maksimum istek
     * @param duration Süre (örn: Duration.ofMinutes(1))
     * @return true → izin verildi, false → limit aşıldı
     */
    public boolean isAllowed(String ip, String method, String endpoint, int limit, Duration duration) {
        return redisService.isIpRequestAllowed(ip, method, endpoint, limit, duration);
    }

    /**
     * Mevcut istek sayısını getirir.
     */
    public long getCurrentCount(String ip, String method, String endpoint) {
        return redisService.getCurrentIpRequestCount(ip, method, endpoint);
    }

    /**
     * Kalan TTL (Time To Live) süresini getirir (saniye).
     */
    public long getRemainingTime(String ip, String method, String endpoint) {
        return redisService.getIpRequestRemainingTime(ip, method, endpoint);
    }

    /**
     * Limiti manuel sıfırlar.
     */
    public void resetLimit(String ip, String method, String endpoint) {
        redisService.resetIpLimit(ip, method, endpoint);
    }

}
