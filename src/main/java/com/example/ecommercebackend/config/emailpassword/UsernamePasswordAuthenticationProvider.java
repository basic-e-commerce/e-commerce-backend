package com.example.ecommercebackend.config.emailpassword;


import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.UnAuthorizedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final CustomerUserDetailsService customerUserDetailsService;
    private final AdminUserDetailsService adminUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UsernamePasswordAuthenticationProvider(CustomerUserDetailsService customerUserDetailsService, AdminUserDetailsService adminUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.adminUserDetailsService = adminUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // --- USER YÜKLEME ---
        User user;

        Object details = authentication.getDetails();

        if (details instanceof Admin) {
            System.out.println("------------admin");
            user = (User) adminUserDetailsService.loadUserByUsername(username);
        } else if (details instanceof Customer) {
            System.out.println("------------customer");
            user = (User) customerUserDetailsService.loadUserByUsername(username);
        } else {
            throw new BadRequestException("Geçersiz user tipi");
        }

        if (!user.isAccountNonExpired() || !user.isEnabled())
            throw new BadRequestException("Lütfen hesabınızı onaylatın");

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Kullanıcı Adı Veya Parola Yanlıştır");
        }
        return createSuccessAuthentication(username,authentication,user);
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {

        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
