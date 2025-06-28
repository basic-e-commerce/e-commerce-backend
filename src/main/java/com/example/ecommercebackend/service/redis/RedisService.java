package com.example.ecommercebackend.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveData(String key, String value,Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    // ✔️ 🔥 Ziyaretçi Sayaçları 🔥

    private static final String TOTAL_VISITOR_KEY = "visitor:total";
    private static final String UNIQUE_VISITOR_KEY = "visitor:unique";
    private static final String DAILY_VISITOR_PREFIX = "visitor:daily:";

    // 🔸 Toplam ziyaretçiyi artır
    public void incrementTotalVisitor() {
        redisTemplate.opsForValue().increment(TOTAL_VISITOR_KEY);
    }

    public Long getTotalVisitor() {
        String value = redisTemplate.opsForValue().get(TOTAL_VISITOR_KEY);
        return value == null ? 0L : Long.parseLong(value);
    }

    // 🔸 Unique (benzersiz) ziyaretçi IP kaydı
    public void logUniqueVisitor(String ip) {
        redisTemplate.opsForSet().add(UNIQUE_VISITOR_KEY, ip);
    }

    public Long getUniqueVisitorCount() {
        return redisTemplate.opsForSet().size(UNIQUE_VISITOR_KEY);
    }

    // 🔸 Günlük ziyaretçi kaydı
    public void logDailyVisitor(String ip) {
        String todayKey = DAILY_VISITOR_PREFIX + LocalDate.now();
        redisTemplate.opsForSet().add(todayKey, ip);
        redisTemplate.expire(todayKey, Duration.ofDays(30)); // 30 gün sakla
    }

    public Long getDailyVisitorCount(LocalDate date) {
        String key = DAILY_VISITOR_PREFIX + date;
        return redisTemplate.opsForSet().size(key);
    }


    // =====================================
    // 🔥 🔥 IP Bazlı Rate Limiting 🔥 🔥
    // =====================================

    /**
     * Rate Limiting Kontrolü
     * @param ip - Client IP
     * @param method - HTTP Method (GET, POST...)
     * @param endpoint - URI (örneğin /api/products)
     * @param limit - İzin verilen maksimum istek
     * @param duration - Süre (örneğin Duration.ofMinutes(1))
     * @return true → izin verildi, false → limit aşıldı
     */
    public boolean isIpRequestAllowed(String ip, String method, String endpoint, int limit, Duration duration) {
        String key = String.format("rate:ip:%s:%s:%s", ip, method, endpoint);

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, duration);
        }

        return count <= limit;
    }

    /**
     * Şu anki istek sayısını getirir
     */
    public Long getCurrentIpRequestCount(String ip, String method, String endpoint) {
        String key = String.format("rate:ip:%s:%s:%s", ip, method, endpoint);
        String value = redisTemplate.opsForValue().get(key);
        return value == null ? 0L : Long.parseLong(value);
    }

    /**
     * Kalan süreyi getirir
     */
    public Long getIpRequestRemainingTime(String ip, String method, String endpoint) {
        String key = String.format("rate:ip:%s:%s:%s", ip, method, endpoint);
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Limiti sıfırlar
     */
    public void resetIpLimit(String ip, String method, String endpoint) {
        String key = String.format("rate:ip:%s:%s:%s", ip, method, endpoint);
        redisTemplate.delete(key);
    }



}
