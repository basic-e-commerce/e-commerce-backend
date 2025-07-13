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

//			if (cityRepository.findAll().isEmpty()) {
//				WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
//				String responseJson = webClient.get()
//						.uri("/cities?countryCode=TR")
//						.retrieve()
//						.bodyToMono(String.class)
//						.block();
//
//
//				// responseJson: gelen JSON string
//				ObjectMapper mapper = new ObjectMapper();
//
//				JsonNode root = null;
//				try {
//					root = mapper.readTree(responseJson);
//
//					boolean result = root.get("result").asBoolean();
//					String additionalMessage = root.get("additionalMessage").asText();
//
//					if (result && (additionalMessage != null && additionalMessage.equals("Success"))) {
//						JsonNode dataNode = root.get("data");
//						// dataNode bir array (dizi) olduğu için
//						List<CityDto> cityList = new ArrayList<>();
//
//						if (dataNode.isArray()) {
//							for (JsonNode cityNode : dataNode) {
//								// CityDto'ya dönüştürmek için ObjectMapper kullan
//								CityDto city = mapper.treeToValue(cityNode, CityDto.class);
//								System.out.println(city.getName());
//								System.out.println(city.getCityCode());
//								cityList.add(city);
//							}
//						}
//
//						cityList.forEach(cityDto -> {
//							City city = new City(
//									cityDto.getName(),
//									cityDto.getCityCode(),
//									cityDto.getCountryCode()
//							);
//							cityRepository.save(city);
//						});
//
//						if (districtRepository.findAll().isEmpty()) {
//							for (CityDto cityDto : cityList) {
//								String responseJsonDistrict = webClient.get()
//										.uri("/districts?countryCode=TR&cityCode=%s".formatted(cityDto.getCityCode()))
//										.retrieve()
//										.bodyToMono(String.class)
//										.block();
//
//								ObjectMapper mapperDistrict = new ObjectMapper();
//
//								JsonNode rootDistrict = null;
//								try {
//									rootDistrict = mapperDistrict.readTree(responseJsonDistrict);
//
//									boolean resultDistrict = rootDistrict.get("result").asBoolean();
//									String additionalMessageDistrict = rootDistrict.get("additionalMessage").asText();
//
//									if (resultDistrict && (additionalMessageDistrict != null && additionalMessageDistrict.equals("Success"))) {
//										JsonNode dataNodeDistrict = rootDistrict.get("data");
//										// dataNode bir array (dizi) olduğu için
//										List<DistrictDto> districtDtoList = new ArrayList<>();
//
//										if (dataNode.isArray()) {
//											for (JsonNode districtNode : dataNodeDistrict) {
//												// CityDto'ya dönüştürmek için ObjectMapper kullan
//												DistrictDto districtDto = mapper.treeToValue(districtNode, DistrictDto.class);
//												districtDtoList.add(districtDto);
//											}
//										}
//
//										districtDtoList.forEach(districtDto -> {
//											District district = new District(
//													districtDto.getName(),
//													districtDto.getDistrictID(),
//													districtDto.getCityCode(),
//													districtDto.getCountryCode()
//											);
//											System.out.println(district.getName()+" "+district.getCityCode());
//											if (!districtRepository.existsByDistrictId(districtDto.getDistrictID())) {
//												districtRepository.save(district);
//											}
//
//										});
//
//										districtDtoList.clear();
//
//									}else
//										throw new BadRequestException("3. parti servisinde hata bulunmaktadır! Lütfen daha sonra tekrar deneyiniz.");
//
//								} catch (JsonProcessingException e) {
//									throw new BadRequestException(e.getMessage());
//								}
//							}
//						}
//
//					}else
//						throw new BadRequestException("3. parti servisinde hata bulunmaktadır! Lütfen daha sonra tekrar deneyiniz.");
//
//				} catch (JsonProcessingException e) {
//					throw new BadRequestException(e.getMessage());
//				}
//
//
//			}

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
						"5559876758"
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
								"5559876758"
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
