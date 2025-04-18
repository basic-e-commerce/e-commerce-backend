package com.example.ecommercebackend.filter;

import com.example.ecommercebackend.config.ApplicationConstant;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.service.auth.JwtService;
import com.example.ecommercebackend.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtValidationFilter.class);
    private final JwtService jwtService;
    private final UserService userService;

    public JwtValidationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(ApplicationConstant.JWT_HEADER.getMessage());
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        try {
            response.setHeader(ApplicationConstant.JWT_HEADER.getMessage(), jwt);
            Claims claims = jwtService.getClaims(jwt);
            Authentication authentication = createAuthentication(claims, request, response);
            if (authentication == null) return;
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("JWT validation successful for user: {}", claims.getSubject());
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired: {}", ex.getMessage());
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired", request);
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.error("Invalid JWT: {}", ex.getMessage());
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token", request);
            return;
        }catch (NotFoundException ex){
            logger.error("User not found: {}", ex.getMessage());
            writeErrorResponse(response, HttpStatus.NOT_FOUND, "User not found", request);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(Claims claims, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = claims.getSubject();
            User user = userService.getUserByUsername(username);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return auth;
        } catch (NullPointerException npe) {
            logger.error("User not found or token is invalid");
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired", request);
            return null; // Hata durumunda authentication oluşturma
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/auth/**");
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, HttpServletRequest request) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        String jsonResponse = String.format("""
        {
            "timestamp": "%s",
            "status": %d,
            "error": "%s",
            "message": "%s",
            "path": "%s"
        }
        """,
                java.time.Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
