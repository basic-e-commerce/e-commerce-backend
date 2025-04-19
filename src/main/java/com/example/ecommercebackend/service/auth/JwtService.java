package com.example.ecommercebackend.service.auth;

import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.InvalidFormatException;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private  String SECRET_KEY;

    @Value("${jwt.refreshAge}")
    private long REFRESH_TOKEN_EXPIRATION; // 7 gün

    public JwtService() {
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.SECRET_KEY = dotenv.get("JWT_SECRET");
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateAccessToken(String username) {
        // 15 dakika
        long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000 * 10;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // Token'in payload kısmını döndürür.
        }catch (ExpiredJwtException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        String TOKEN_PREFIX = "Bearer ";
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String substring = token.substring(TOKEN_PREFIX.length());
            Claims claims = getClaims(substring);
            return claims.getExpiration().before(new Date());
        }
        throw new InvalidFormatException(ExceptionMessage.EXPIRED_TOKEN.getMessage());
    }

    public String hashToken(String token) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);  // Base64 ile encode ederek veritabanında saklanabilir
    }
}
