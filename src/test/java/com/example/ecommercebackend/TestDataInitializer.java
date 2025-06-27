package com.example.ecommercebackend;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.AdminRepository;
import com.example.ecommercebackend.repository.user.CustomerRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import com.example.ecommercebackend.service.user.AdminService;
import com.example.ecommercebackend.service.user.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Set;


@TestConfiguration
public class TestDataInitializer {
    @Bean
    public CommandLineRunner initTestData(
            RoleRepository roleRepository,
            CountryRepository countryRepository,
            MerchantRepository merchantRepository,
            CustomerRepository customerRepository,
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (!roleRepository.existsByRoleNameEqualsIgnoreCase("ADMIN")) {
                roleRepository.save(new Role("ADMIN"));
            }

            if (!roleRepository.existsByRoleNameEqualsIgnoreCase("CUSTOMER")) {
                roleRepository.save(new Role("CUSTOMER"));
            }

            if (!roleRepository.existsByRoleNameEqualsIgnoreCase("GUEST")) {
                roleRepository.save(new Role("GUEST"));
            }

            if (countryRepository.findByUpperName("TURKIYE").isEmpty()) {
                countryRepository.save(new Country(
                        "TR", "Türkiye", "TURKIYE", "TUR", (short) 792, 90
                ));
            }

            if (merchantRepository.findAll().isEmpty()) {
                merchantRepository.save(new Merchant(
                        "demo",
                        new Address(
                                "Mağaza Adresi",
                                countryRepository.findByUpperName("TURKIYE").get(),
                                "demo",
                                "demo",
                                "istanbul",
                                "address line 1",
                                "34000",
                                "5559876758"
                        ),
                        null,
                        "5098765432",
                        "feminizmturkiye2000@gmail.com",
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(75),
                        "izcb abhl kkto upek"
                ));
            }

            if (customerRepository.findByUsername("customer@gmail.com").isEmpty()) {
                Customer customer = new Customer(
                        "customer", "customerSoyAd", "5075678909", "customer@gmail.com",
                        passwordEncoder.encode("pass"),
                        Set.of(roleRepository.findByRoleName("CUSTOMER").get()),
                        true, true
                );
                customerRepository.save(customer);
            }

            if (adminRepository.findByUsername("admin@gmail.com").isEmpty()) {
                Admin admin = new Admin(
                        "admin", "admin", "56789089786", "admin@gmail.com",
                        passwordEncoder.encode("admin"),
                        Set.of(roleRepository.findByRoleName("ADMIN").get()),
                        true, true
                );
                adminRepository.save(admin);
            }
        };
    }
}
