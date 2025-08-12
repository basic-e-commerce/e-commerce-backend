package com.example.ecommercebackend.service.auth;

import com.example.ecommercebackend.anotation.NotNullParameter;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationRequestDto;
import com.example.ecommercebackend.dto.user.authentication.AuthenticationResponseDto;
import com.example.ecommercebackend.dto.user.customer.VerificationPasswordDto;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.RefreshToken;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.*;
import com.example.ecommercebackend.service.mail.MailService;
import com.example.ecommercebackend.service.redis.RedisService;
import com.example.ecommercebackend.service.user.AdminService;
import com.example.ecommercebackend.service.user.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final RefreshTokenService refreshTokenService;
    private final RedisService redisService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${cookie.refreshTokenCookie.secure}")
    private boolean secure;
    @Value("${cookie.refreshTokenCookie.sameSite}")
    private String sameSite;
    @Value("${cookie.refreshTokenCookie.path}")
    private String path;
    @Value("${cookie.refreshTokenCookie.refreshmaxAge}")
    private String maxAge;

    @Value("${domain.name}")
    private String domainName;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, CustomerService customerService, AdminService adminService, RefreshTokenService refreshTokenService, RedisService redisService, MailService mailService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customerService = customerService;
        this.adminService = adminService;
        this.refreshTokenService = refreshTokenService;
        this.redisService = redisService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }


    @NotNullParameter
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
                    + "; Secure=" + "true"//secure
                    + "; Max-Age=" + "360000" //Integer.parseInt(maxAge)
                    + "; SameSite=" + "sameSite"//sameSite    Lax
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

    public String logout(String refreshToken, HttpServletResponse response) {
        RefreshToken refresh = refreshTokenService.getRefreshTokenHash(refreshToken);
        refresh.setActive(false);
        refresh.setExpirationTime(LocalDateTime.now());
        refreshTokenService.save(refresh);

        // Cookie silme
        Cookie cookie = new Cookie("refreshToken", null); // aynı isim
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS kullanıyorsanız
        cookie.setPath("/"); // Cookie'nin scope'u
        cookie.setMaxAge(0); // hemen silinsin
        response.addCookie(cookie);

        return "Successfully logged out";
    }

    public String verification(String code, HttpServletResponse response) {
        String link = domainName;

        // 1️⃣ Redis'ten kullanıcı adını al
        String username = (String) redisService.getData(code);

        // Kullanıcı adı yoksa: Süresi geçmiş
        if (username == null) {
            link += "/account-not-verified?message=Onay süresi geçmiştir";
            return redirect(response, link);
        }

        // 2️⃣ Kullanıcıyı bul
        Optional<Customer> optionalCustomer = customerService.findByUsernameNull(username);
        if (optionalCustomer.isEmpty()) {
            link += "/account-not-verified?username=" + username + "&message=Kullanıcı bulunamamıştır";
            return redirect(response, link);
        }

        // 3️⃣ Onayla
        Customer customer = optionalCustomer.get();
        customer.setEnabled(true);
        customer.setAccountNonLocked(true);
        customerService.save(customer);

        // 4️⃣ Başarılı sayfaya yönlendir
        link += "/account-verified?username=" + username;
        return redirect(response, link);
    }

    // Yardımcı method
    private String redirect(HttpServletResponse response, String link) {
        try {
            response.sendRedirect(link);
        } catch (IOException e) {
            throw new BadRequestException("Yönlendirme Hatası!");
        }
        return link;
    }

    public String resetPassword(String username) {
        Customer customer = customerService.findByUsername(username);

        String generateCode = String.valueOf(100000 + (int)(Math.random() * 900000));
        redisService.saveData(generateCode,customer.getUsername(), Duration.ofMinutes(30));
        System.out.println("----------"+customer.getUsername());
        String link = domainName + "/password-reset?code=" + generateCode;

        String onayKodu = mailService.send(customer.getUsername(),"Şifre resetleme linki",link);
        System.out.println(onayKodu);
        System.out.println("reset link: "+link);
        return "Şifres sıfırlama linki emaile gönderilmiştir!";
    }

    public String verificationPassword(VerificationPasswordDto verificationPasswordDto) {
        String username = (String) redisService.getData(verificationPasswordDto.getCode());
        if (username == null)
            throw new BadRequestException("Geçerli User bulunamadı");

        if (!verificationPasswordDto.getPassword().equals(verificationPasswordDto.getRePassword()))
            throw new BadRequestException("Şifreler Eşleşmiyor");

        Customer customer = customerService.findByUsername(username);
        String hashPassword = passwordEncoder.encode(verificationPasswordDto.getPassword());
        customer.setPassword(hashPassword);
        customerService.save(customer);
        redisService.deleteData(verificationPasswordDto.getCode());
        return "Şifre Güncellendi!";
    }


}
