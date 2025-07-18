package com.example.ecommercebackend;

import com.example.ecommercebackend.dto.product.shipping.CityDto;
import com.example.ecommercebackend.dto.product.shipping.DistrictDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.merchant.OpenCloseHour;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.entity.user.District;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.AdminRepository;
import com.example.ecommercebackend.repository.user.CityRepository;
import com.example.ecommercebackend.repository.user.DistrictRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class ECommerceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(RoleRepository roleRepository, MerchantRepository merchantRepository, CountryRepository countryRepository, CityRepository cityRepository, DistrictRepository districtRepository, WebClient.Builder webClientBuilder) {
		return args -> {

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
				List<OpenCloseHour> openCloseHours = new LinkedList<>();
				OpenCloseHour pazartesi = new OpenCloseHour("pazartesi","09:00","18:00");
				OpenCloseHour sali = new OpenCloseHour("sali","09:00","18:00");
				OpenCloseHour carsamba = new OpenCloseHour("çarşamba","09:00","18:00");
				OpenCloseHour persembe = new OpenCloseHour("perşembe","09:00","18:00");
				OpenCloseHour cuma = new OpenCloseHour("cuma","09:00","18:00");
				OpenCloseHour cumartesi = new OpenCloseHour("cumartesi","09:00","18:00");
				OpenCloseHour pazar = new OpenCloseHour("pazar","09:00","18:00");
				openCloseHours.add(pazartesi);
				openCloseHours.add(sali);
				openCloseHours.add(carsamba);
				openCloseHours.add(persembe);
				openCloseHours.add(cuma);
				openCloseHours.add(cumartesi);
				openCloseHours.add(pazar);

				List<Address> sendingAddress = new ArrayList<>();
				Address address = new Address(
						"Mağaza Adresi",
						countryRepository.findByUpperName("TURKIYE").get(),
						"demo",
						"demo",
						cityRepository.findByCityCode("34").orElse(null),
						districtRepository.findByDistrictId(107062).orElse(null),
						"address line 1",
						"34000",
						"5559876758",
						false
				);
				sendingAddress.add(address);

				Merchant merchant = new Merchant(
						"demo",
						new Address(
								"Mağaza Adresi",
								countryRepository.findByUpperName("TURKIYE").get(),
								"demo",
								"demo",
								cityRepository.findByCityCode("34").orElse(null),
								districtRepository.findByDistrictId(107062).orElse(null),
								"address line 1",
								"34000",
								"5559876758",
								false
								),
						"addresslink",
						sendingAddress,
						null,
						"5098765432",
						"tel:5098765432",
						"feminizmturkiye2000@gmail.com",
						"mailto:feminizmturkiye2000@gmail.com",
						BigDecimal.valueOf(1000),
						BigDecimal.valueOf(75),
						"izcb abhl kkto upek",
						"instagram",
						"instagram link",
						"wplink",
						"footerdesc",
						openCloseHours
				);
				merchantRepository.save(merchant);
			}



		};
	}



}
