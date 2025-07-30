package com.example.ecommercebackend.config;

import com.example.ecommercebackend.config.emailpassword.AdminUserDetailsService;
import com.example.ecommercebackend.config.emailpassword.CustomerUserDetailsService;
import com.example.ecommercebackend.config.emailpassword.UsernamePasswordAuthenticationProvider;
import com.example.ecommercebackend.filter.JwtValidationFilter;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final CustomerUserDetailsService customerUserDetailService;
    private final AdminUserDetailsService adminUserDetailService;
    private final JwtValidationFilter jwtValidationFilter;

    public SecurityConfig(CustomerUserDetailsService customerUserDetailService, AdminUserDetailsService adminUserDetailService, JwtValidationFilter jwtValidationFilter) {
        this.customerUserDetailService = customerUserDetailService;
        this.adminUserDetailService = adminUserDetailService;
        this.jwtValidationFilter = jwtValidationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x->x
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/category").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/category/parent").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/category/by-link-name").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/category").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/category").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/category/image").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/category/image").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/mail").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/supplier").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/supplier").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/supplier/id").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/tag").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/tag").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/tag/id").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/attribute").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/attribute/id").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/attribute").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/attribute-value").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/attribute-value/id").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/attribute-value").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/attribute-value/attribute").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/product/simple").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/product").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/product").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/product/product-image").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/product/product-image").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/product/cover-image").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/product/cover-image").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/product/name/admin/{name}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/product/filter").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/product/filter/small/link-name").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/product/filter/small/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/product/name/{linkName}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/product/name/admin/{linkName}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/product").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/merchant").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/merchant").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/merchant").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/v1/merchant/image").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/merchant/add-sending-address").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/merchant/remove-sending-address").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/merchant/select-default-address").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/merchant/list-sending-address").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/customer/address").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/customer/address").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/customer/address").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer/address").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/customer/update-password").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/customer/profile").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer/profile").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/customer").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/customer/re-verification-code").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/verification/{code}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/c-login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/a-login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/refresh/logout").hasAnyAuthority("ADMIN","CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/auth/is-auth").hasAnyAuthority("ADMIN","CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/v1/auth/verification-password").permitAll()


                        .requestMatchers(HttpMethod.GET,"/api/v1/order/user").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/order/filter").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/order/total-price").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/order").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/order/by-order-code").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/order/cargo-offer").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/order/offer-approve").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/order/cargo-refund").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/order/cargo-cancel").hasAuthority("ADMIN")


                        .requestMatchers(HttpMethod.PUT,"/api/v1/card").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/card/add-coupon").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/card/remove-coupon").hasAuthority("CUSTOMER")

                        .requestMatchers(HttpMethod.GET,"/api/v1/card-item/by-ids").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/coupon").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/coupon").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/payment").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/payment/payCallBack").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/payment/bin").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/payment/refund").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/payment/max-refund").hasAuthority("ADMIN")


                        .requestMatchers(HttpMethod.POST,"/api/v1/sell").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/sell/day-sell").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/sell/customer-register").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/sell/sell-product").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/sell/card-contain-product").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/sell/low-product").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/admin").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/visitors/visit").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/visitors/total").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/visitors/unique").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/visitors/today").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/visitors/daily").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/visitors/last-ten").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/visitors/between-visitor").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/customer-coupon").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer-coupon/customer").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/customer-coupon/coupon").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/district").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/district/district-id").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/district").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/district/city-code").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/district/district-id").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/city").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/city").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/city/code").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/city/code").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/shipping-template").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-template").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/shipping-template").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-template/balance").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-template/offer").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-address/city").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-address/district").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/shipping-address/create-sending-address").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/shipping-address/create-receipt-address").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-address/get-all").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/shipping-address/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-address/get/{id}").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/shipping-cargo").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/shipping-cargo/offer-approve").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/shipping-cargo/offer-cancel").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/shipping-cargo/detail").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/shipping-cargo/refund").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/shipping-cargo/cancel").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/product-template").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/product-template/list").hasAuthority("ADMIN")

                        .anyRequest().permitAll())
                .anonymous(anonymous -> anonymous
                        .principal("anonymousUser") // Opsiyonel: Principal adını belirtebilirsin
                        .authorities("ROLE_ANONYMOUS"))
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration ccfg = new CorsConfiguration();
            ccfg.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5173","https://sandbox-api.iyzipay.com","https://litysofttest1.site","https://app.geliver.io","https://www.app.geliver.io"));
            ccfg.setAllowedMethods(Collections.singletonList("*"));
            ccfg.setAllowCredentials(true);
            ccfg.setAllowedHeaders(Collections.singletonList("*"));
            ccfg.setExposedHeaders(List.of("Authorization"));
            ccfg.setMaxAge(3600L);
            return ccfg;
        };
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {  // circular bağımlılık oldugu için static yaparak ilk başta yüklenmesinşi sağladık26
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Varsayılan AuthenticationManager'ı al
        AuthenticationManager defaultAuthManager = authenticationConfiguration.getAuthenticationManager();

        // Özel AuthenticationProvider'larınızı oluşturun
        List<AuthenticationProvider> customProviders = List.of(
                new UsernamePasswordAuthenticationProvider(customerUserDetailService,adminUserDetailService, passwordEncoder())
        );

        // Özel provider'lar ile bir ProviderManager oluştur
        ProviderManager customAuthManager = new ProviderManager(customProviders);

        return authentication -> {
            try {
                // Öncelikle özel sağlayıcılar ile doğrulama yap
                return customAuthManager.authenticate(authentication);
            } catch (AuthenticationException e) {
                // Özel sağlayıcı başarısız olursa varsayılan AuthenticationManager'ı dene
                return defaultAuthManager.authenticate(authentication);
            }
        };
    }
}
