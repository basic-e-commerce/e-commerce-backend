package com.example.ecommercebackend.service.auth;

import com.example.ecommercebackend.dto.user.authentication.AuthenticationRequestDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationResponseDto;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Value("${cookie.refreshTokenCookie.secure}")
    private boolean secure;
    @Value("${cookie.refreshTokenCookie.sameSite}")
    private String sameSite;
    @Value("${cookie.refreshTokenCookie.path}")
    private String path;
    @Value("${cookie.refreshTokenCookie.refreshmaxAge}")
    private String maxAge;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    public AuthenticationResponseDto loginCustomer(AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {

        /**
         // Giriş bilgilerinin formatını kontrol et
         if (regexValidation.isValidEmail(authenticationRequestDto.getUsername()) && regexValidation.isValidPassword(authenticationRequestDto.getPassword())) {
         throw new InvalidFormatException(ApplicationConstant.INVALID_FORMAT);
         }**/

        // Authentication nesnesi oluşturuluyor
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequestDto.getUsername(),
                authenticationRequestDto.getPassword()
        );
        authenticationRequest.setDetails(new Customer());

        // Kimlik doğrulama işlemi
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationRequest);

        // Eğer kullanıcı doğrulanamazsa hata fırlat
        if (!authenticatedUser.isAuthenticated())
            throw new NotFoundException(ExceptionMessage.WRONG_CREDENTIALS.getMessage());


        String accessToken = jwtService.generateAccessToken(authenticatedUser.getName());
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser.getName());

        try {
            String refreshHash = jwtService.hashToken(refreshToken);
            String hash = refreshHash;
            // Set-Cookie başlığı ile cookie'yi gönder
            response.addHeader("Set-Cookie", "refresh_token=" + hash
                    + "; Path=" + path
                    + "; HttpOnly"
                    + "; Secure=" + secure
                    + "; Max-Age=" + Integer.parseInt(maxAge)
                    + "; SameSite=" + sameSite);

            User user = userService.getUserByUsername(authenticatedUser.getName());
            return new AuthenticationResponseDto(accessToken, user.getFirstName(),user.getLastName(), user.getUsername());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public AuthenticationResponseDto loginAdmin(AuthenticationRequestDto authenticationRequestDto,HttpServletResponse response) {
        /**
         // Giriş bilgilerinin formatını kontrol et
         if (regexValidation.isValidEmail(authenticationRequestDto.getUsername()) && regexValidation.isValidPassword(authenticationRequestDto.getPassword())) {
         throw new InvalidFormatException(ApplicationConstant.INVALID_FORMAT);
         } **/

        // Authentication nesnesi oluşturuluyor
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequestDto.getUsername(),
                authenticationRequestDto.getPassword()
        );
        authenticationRequest.setDetails(new Admin());
        // Kimlik doğrulama işlemi
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationRequest);

        // Eğer kullanıcı doğrulanamazsa hata fırlat
        if (!authenticatedUser.isAuthenticated())
            throw new NotFoundException(ExceptionMessage.WRONG_CREDENTIALS.getMessage());


        String accessToken = jwtService.generateAccessToken(authenticatedUser.getName());
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser.getName());

        try {
            String refreshHash = jwtService.hashToken(refreshToken);
            String hash = refreshHash; //refreshTokenService.createRefreshToken(authenticatedUser.getName(),refreshHash);
            // Set-Cookie başlığı ile cookie'yi gönder
            response.addHeader("Set-Cookie", "refresh_token=" + hash
                    + "; Path=" + path
                    + "; HttpOnly"
                    + "; Secure=" + secure
                    + "; Max-Age=" + Integer.parseInt(maxAge)
                    + "; SameSite=" + sameSite);

            User user = userService.getUserByUsername(authenticatedUser.getName());
            return new AuthenticationResponseDto(accessToken, user.getFirstName(), user.getLastName(), user.getUsername());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
