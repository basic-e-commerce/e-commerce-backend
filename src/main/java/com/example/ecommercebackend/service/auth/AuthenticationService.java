package com.example.ecommercebackend.service.auth;

import com.example.ecommercebackend.dto.user.authentication.AuthenticationRequestDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationResponseDto;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.RefreshToken;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.TokenExpiredException;
import com.example.ecommercebackend.exception.UnAuthorizedException;
import com.example.ecommercebackend.service.user.AdminService;
import com.example.ecommercebackend.service.user.CustomerService;
import com.example.ecommercebackend.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final RefreshTokenService refreshTokenService;

    @Value("${cookie.refreshTokenCookie.secure}")
    private boolean secure;
    @Value("${cookie.refreshTokenCookie.sameSite}")
    private String sameSite;
    @Value("${cookie.refreshTokenCookie.path}")
    private String path;
    @Value("${cookie.refreshTokenCookie.refreshmaxAge}")
    private String maxAge;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, CustomerService customerService, AdminService adminService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customerService = customerService;
        this.adminService = adminService;
        this.refreshTokenService = refreshTokenService;
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

        Customer customer = customerService.findByUsername(authenticationRequestDto.getUsername());

        String accessToken = jwtService.generateAccessToken(customer.getUsername());
        String refreshToken = jwtService.generateRefreshToken(customer.getUsername());

        try {
            String refreshHash = jwtService.hashToken(refreshToken);
            String hash = refreshTokenService.createRefreshToken(customer, refreshHash);
            // Set-Cookie başlığı ile cookie'yi gönder
            response.addHeader("Set-Cookie", "refresh_token=" + hash
                    + "; Path=" + "/"//path
                    + "; HttpOnly"
                    + "; Secure=" + "false"//secure
                    + "; Max-Age=" + "360000" //Integer.parseInt(maxAge)
                    + "; SameSite=" + "Lax"//sameSite
                    );

            return new AuthenticationResponseDto(accessToken, customer.getFirstName(),customer.getLastName(), customer.getUsername(),"CUSTOMER");
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

        Admin admin = adminService.findByUsername(authenticationRequestDto.getUsername());

        String accessToken = jwtService.generateAccessToken(admin.getUsername());
        String refreshToken = jwtService.generateRefreshToken(admin.getUsername());

        try {
            String refreshHash = jwtService.hashToken(refreshToken);
            String hash = refreshTokenService.createRefreshToken(admin,refreshHash);
            // Set-Cookie başlığı ile cookie'yi gönder
            response.addHeader("Set-Cookie", "refresh_token=" + hash
                    + "; Path=" + "/"//path
                    + "; HttpOnly"
                    + "; Secure=" + "false"//secure
                    + "; Max-Age=" + "360000" //Integer.parseInt(maxAge)
                    + "; SameSite=" + "Lax"//sameSite
            );
            


            return new AuthenticationResponseDto(accessToken, admin.getFirstName(), admin.getLastName(), admin.getUsername(),"ADMIN");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean isAuth() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public AuthenticationResponseDto refresh(String refreshTokenHash) {
        RefreshToken refreshToken = refreshTokenService.getRefreshTokenHash(refreshTokenHash);

        if (refreshToken.isActive() && refreshToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            User user = refreshToken.getUser();

            String role = user.getAuthorities().stream().findFirst().get().getAuthority();

            return  new AuthenticationResponseDto(jwtService.generateAccessToken(refreshToken.getUser().getUsername()),user.getFirstName(), user.getLastName(), user.getUsername(),role);

        }else
            throw new TokenExpiredException(ExceptionMessage.TRY_LOGIN.getMessage());
    }

    public String logout(String refreshToken) {
        RefreshToken refresh = refreshTokenService.getRefreshTokenHash(refreshToken);
        refresh.setActive(false);
        refresh.setExpirationTime(LocalDateTime.now());
        refreshTokenService.save(refresh);
        return "Successfully logged out";
    }
}
