package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.service.redis.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class VisitorService {
    private final RedisService redisService;

    public VisitorService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void visit() {
        String ip = getClientIp();
        redisService.incrementTotalVisitor();
        redisService.logUniqueVisitor(ip);
        redisService.logDailyVisitor(ip);
    }

    public Long getTotalVisitor() {
        return redisService.getTotalVisitor();
    }

    public Long getUniqueVisitor() {
        return redisService.getUniqueVisitorCount();
    }

    public Long getDailyVisitor(LocalDate date) {
        return redisService.getDailyVisitorCount(date);
    }

    public Long getTodayVisitor() {
        return getDailyVisitor(LocalDate.now());
    }

    private String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        String xfHeader = request.getHeader("X-Forwarded-For");
        return xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];
    }

    public Map<LocalDate, Long> getLastTenVisitor() {
        Map<LocalDate, Long> visitorsMap = new LinkedHashMap<>(); // sırayı korumak için LinkedHashMap kullanalım
        LocalDate today = LocalDate.now();

        for (int i = 9; i >= 0; i--) {  // 9'dan 0'a, son 10 gün (bugün dahil)
            LocalDate date = today.minusDays(i);
            Long count = redisService.getDailyVisitorCount(date);
            visitorsMap.put(date, count != null ? count : 0L); // count null ise 0 koy
        }

        return visitorsMap;    }
}
