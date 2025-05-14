package com.example.ecommercebackend.service.auth;

import com.example.ecommercebackend.entity.user.RefreshToken;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.user.RefreshTokenRepository;
import com.example.ecommercebackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refreshAge}")
    private int refreshExp;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(User user,String refreshTokenHash) {
        RefreshToken refreshToken = new RefreshToken(user,refreshTokenHash, LocalDateTime.now().plusNanos(refreshExp));
        refreshTokenRepository.save(refreshToken);
        return refreshTokenHash;
    }

    public RefreshToken getRefreshTokenHash(String refreshTokenHash) {
        return refreshTokenRepository.findByRefreshTokenHash(refreshTokenHash).orElseThrow(()-> new NotFoundException("Refresh Token :"+ExceptionMessage.NOT_FOUND.getMessage()));
    }


    public void save(RefreshToken refresh) {
        refreshTokenRepository.save(refresh);
    }
}
