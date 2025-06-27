package com.example.ecommercebackend;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.Admin;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import com.example.ecommercebackend.service.user.AdminService;
import com.example.ecommercebackend.service.user.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ActiveProfiles("test")
class ECommerceBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
