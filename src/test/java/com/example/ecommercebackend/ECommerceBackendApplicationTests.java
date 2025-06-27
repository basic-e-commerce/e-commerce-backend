package com.example.ecommercebackend;

import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
class ECommerceBackendApplicationTests {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Test
	void contextLoads() {
		if (!roleRepository.existsByRoleNameEqualsIgnoreCase("ADMIN")) {
			Role admin = new Role("ADMIN");
			roleRepository.save(admin);
		}

		if (!roleRepository.existsByRoleNameEqualsIgnoreCase("CUSTOMER")) {
			Role customer = new Role("CUSTOMER");
			roleRepository.save(customer);
		}

		if (!roleRepository.existsByRoleNameEqualsIgnoreCase("GUEST")) {
			Role customer = new Role("GUEST");
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
					"feminizmturkiye2000@gmail.com",
					BigDecimal.valueOf(1000),
					BigDecimal.valueOf(75),
					"izcb abhl kkto upek"
			);
			merchantRepository.save(merchant);
		}
	}

}
