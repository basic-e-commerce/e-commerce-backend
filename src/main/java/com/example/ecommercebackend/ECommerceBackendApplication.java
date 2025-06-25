package com.example.ecommercebackend;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.AdminRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ECommerceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(RoleRepository roleRepository, MerchantRepository merchantRepository, CountryRepository countryRepository, AdminRepository adminRepository) {
		return args -> {

			if (!roleRepository.existsByRoleNameEqualsIgnoreCase("ADMIN")) {
				Role admin = new Role("ADMIN");
				roleRepository.save(admin);
			}

			if (!roleRepository.existsByRoleNameEqualsIgnoreCase("CUSTOMER")) {
				Role customer = new Role("CUSTOMER");
				roleRepository.save(customer);
			}

			if(countryRepository.findByUpperName("TURKIYE").isEmpty()){
				Country turkey = new Country();
				turkey.setIso("TR");
				turkey.setName("Türkiye");
				turkey.setUpperName("TURKIYE");
				turkey.setIso3("TUR");
				turkey.setNumCode((short) 792);
				turkey.setPhoneCode(90);
				countryRepository.save(turkey);
			}

			if (merchantRepository.findAll().isEmpty()) {
				Merchant merchant = new Merchant(
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
						"email@gmail.com",
						BigDecimal.valueOf(100),
						BigDecimal.valueOf(75),
						""
				);
				merchantRepository.save(merchant);
			}



		};
	}



}
