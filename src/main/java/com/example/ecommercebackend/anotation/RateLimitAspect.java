package com.example.ecommercebackend.anotation;

import com.example.ecommercebackend.exception.RateLimitExceededException;
import com.example.ecommercebackend.service.redis.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;

@Aspect
@Component
public class RateLimitAspect {
    private final RateLimiterService rateLimiterService;

    public RateLimitAspect(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Around("@annotation(RateLimit)")
    public Object handleRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        // HTTP request al
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();

        String clientIp = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // Annotation parametrelerini al
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method methodObj = signature.getMethod();

        RateLimit rateLimit = methodObj.getAnnotation(RateLimit.class);

        int limit = rateLimit.limit();
        int duration = rateLimit.duration();
        Duration window = Duration.ofMillis(rateLimit.unit().toMillis(duration));

        boolean allowed = rateLimiterService.isAllowed(clientIp, method, uri, limit, window);

        if (!allowed) {
            throw new RateLimitExceededException("Rate limit exceeded. Please try again later.");
        }

        return joinPoint.proceed();
    }


}

