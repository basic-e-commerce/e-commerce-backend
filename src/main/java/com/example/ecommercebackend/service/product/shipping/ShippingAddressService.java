package com.example.ecommercebackend.service.product.shipping;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.product.shipping.CityDto;
import com.example.ecommercebackend.dto.product.shipping.DistrictDto;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.user.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingAddressService {

    private final WebClient.Builder webClientBuilder;
    private String apiKey;
    private final AddressService addressService;

    public ShippingAddressService(WebClient.Builder webClientBuilder, AddressService addressService) {
        this.webClientBuilder = webClientBuilder;
        this.addressService = addressService;
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.apiKey = dotenv.get("GELIVER");
    }

    public List<CityDto> getCities() {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.get()
                .uri("/cities?countryCode=TR")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // responseJson: gelen JSON string
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = null;
        try {
            root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String additionalMessage = root.get("additionalMessage").asText();

            if (result && (additionalMessage != null && additionalMessage.equals("Success"))) {
                JsonNode dataNode = root.get("data");
                // dataNode bir array (dizi) olduğu için
                List<CityDto> cityList = new ArrayList<>();

                if (dataNode.isArray()) {
                    for (JsonNode cityNode : dataNode) {
                        // CityDto'ya dönüştürmek için ObjectMapper kullan
                        CityDto city = mapper.treeToValue(cityNode, CityDto.class);
                        cityList.add(city);
                    }
                }
                return cityList;
            }else
                throw new BadRequestException("3. parti servisinde hata bulunmaktadır! Lütfen daha sonra tekrar deneyiniz.");

        } catch (JsonProcessingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<DistrictDto> getDistricts(@NotNullParam String cityCode) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.get()
                .uri("/districts?countryCode=TR&cityCode=%s".formatted(cityCode))
                .retrieve()
                .bodyToMono(String.class)
                .block();


        // responseJson: gelen JSON string
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = null;
        try {
            root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String additionalMessage = root.get("additionalMessage").asText();

            if (result && (additionalMessage != null && additionalMessage.equals("Success"))) {
                JsonNode dataNode = root.get("data");
                // dataNode bir array (dizi) olduğu için
                List<DistrictDto> districtDtoList = new ArrayList<>();

                if (dataNode.isArray()) {
                    for (JsonNode cityNode : dataNode) {
                        // CityDto'ya dönüştürmek için ObjectMapper kullan
                        DistrictDto districtDto = mapper.treeToValue(cityNode, DistrictDto.class);
                        districtDtoList.add(districtDto);
                    }
                }
                return districtDtoList;
            }else
                throw new BadRequestException("3. parti servisinde hata bulunmaktadır! Lütfen daha sonra tekrar deneyiniz.");

        } catch (JsonProcessingException e) {
            throw new BadRequestException(e.getMessage());
        }


    }

    public String createSendingAddress(AddressApiDto addressApiDto) {
        return null;
    }
}
