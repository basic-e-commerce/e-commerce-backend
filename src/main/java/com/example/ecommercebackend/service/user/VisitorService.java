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
}
