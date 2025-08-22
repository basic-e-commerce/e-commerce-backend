package com.example.ecommercebackend;

import com.example.ecommercebackend.config.EncryptionUtils;
import com.example.ecommercebackend.dto.product.shipping.CityDto;
import com.example.ecommercebackend.dto.product.shipping.DistrictDto;
import com.example.ecommercebackend.dto.user.admin.AdminCreateDto;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.merchant.OpenCloseHour;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.entity.user.District;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.init.InitLoader;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.AdminRepository;
import com.example.ecommercebackend.repository.user.CityRepository;
import com.example.ecommercebackend.repository.user.DistrictRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import com.example.ecommercebackend.service.merchant.MerchantService;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import com.example.ecommercebackend.service.user.AdminService;
import com.example.ecommercebackend.service.user.CityService;
import com.example.ecommercebackend.service.user.DistrictService;
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
import java.util.stream.Stream;

@SpringBootApplication
public class ECommerceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(InitLoader initLoader,
								  MerchantRepository merchantRepository,
								  CountryRepository countryRepository,
								  CityRepository cityRepository,
								  DistrictRepository districtRepository,
								  MerchantService merchantService,
								  AdminService adminService) {
		return args -> {
			initLoader.loadInitialCountrySql();
			if (adminService.findAll().isEmpty()){
				AdminCreateDto adminCreateDto = new AdminCreateDto(
						"Ugur Soner",
						"Dogan",
						"gidagulce@gmail.com",
						"Gidagulce!17",
						"Gidagulce!17",
						"+905416877502"
				);
				adminService.createAdmin(adminCreateDto);
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
						"Ugur Soner",
						"Dogan",
						"gidagulce@gmail.com",
						cityRepository.findByCityCode("34").orElse(null),
						districtRepository.findByDistrictId(107062).orElse(null),
						"address",
						"34000",
						"+905416877502",
						false,
						true
				);


				sendingAddress.add(address);

				Merchant merchant = new Merchant(
						"Gülce Gıda",
						new Address(
								"Mağaza Adresi",
								countryRepository.findByUpperName("TURKIYE").get(),
								"Ugur Soner",
								"Doğan",
								"gidagulce@gmail.com",
								cityRepository.findByCityCode("34").orElse(null),
								districtRepository.findByDistrictId(107062).orElse(null),
								"Çanakkale Merkez",
								"34000",
								"+905416877502",
								false,
								true
								),
						"addresslink",
						sendingAddress,
						null,
						"+905098765432",
						"gidagulce@gmail.com",
						BigDecimal.valueOf(1000),
						BigDecimal.valueOf(75),
						"jobw hfxr lspm kgej",
						"instagram",
						"instagram link",
						"Footer Açıklama",
						openCloseHours
				);
				merchant.setGeliver(true);
				Merchant save = merchantRepository.save(merchant);
				merchantService.selectDefaultSendingAddress(save.getSendingAddresses().getFirst().getId());
			}

			System.out.println(6);










		};
	}



}
