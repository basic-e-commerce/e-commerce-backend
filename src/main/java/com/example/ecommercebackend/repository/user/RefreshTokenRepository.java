package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.entity.user.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByRefreshTokenHash(String token);
}
