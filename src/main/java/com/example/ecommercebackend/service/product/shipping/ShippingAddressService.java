package com.example.ecommercebackend.service.product.shipping;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.*;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.service.user.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingAddressService {

    private static final Logger log = LoggerFactory.getLogger(ShippingAddressService.class);
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

    public AddressReceiptDto createSendingAddress(AddressApiDto addressApiDto) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.post()
                .uri("/addresses")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(addressApiDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("4xx Client Error: {}", body);
                                    return new BadRequestException("API 4xx error: " + body);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("5xx Server Error: {}", body);
                                    return new RuntimeException("API 5xx error: " + body);
                                })
                )
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println("Giden JSON: " + mapper.writeValueAsString(addressApiDto));

            JsonNode root = mapper.readTree(responseJson);
            boolean result = root.get("result").asBoolean();

            if (result){
                JsonNode dataNode = root.get("data");
                System.out.println(dataNode);
                return mapper.treeToValue(dataNode, AddressReceiptDto.class);
            }else
                throw new BadRequestException("Kullanıcının kargo adresi oluşturulamadı.");

        } catch (JsonMappingException e) {
            throw new BadRequestException("Json datası eşleşmiyor.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json datası işlenemedi.");
        }

    }

    public AddressReceiptDto createReceivingAddress(@NotNullParam AddressApiDto addressApiDto) throws JsonProcessingException {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.post()
                .uri("/addresses")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(addressApiDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("4xx Client Error: {}", body);
                                    return new BadRequestException("API 4xx error: " + body);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("5xx Server Error: {}", body);
                                    return new RuntimeException("API 5xx error: " + body);
                                })
                )
                .bodyToMono(String.class)
                .block();


        System.out.println("-------asddsasdadsa");

        getAllAddress(true,10,0);

        ObjectMapper mapper = new ObjectMapper();


        System.out.println("Giden JSON: " + mapper.writeValueAsString(addressApiDto));

        JsonNode root = mapper.readTree(responseJson);
        boolean result = root.get("result").asBoolean();

        if (result){
            JsonNode dataNode = root.get("data");
            System.out.println(dataNode);
            return mapper.treeToValue(dataNode, AddressReceiptDto.class);
        }else
             throw new BadRequestException("Kullanıcının kargo adresi oluşturulamadı.");

    }

    public AddressShipResponse getAllAddress(@NotNullParam Boolean isRecipientAddress,@NotNullParam Integer limit,@NotNullParam Integer page){
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/addresses")
                        .queryParam("isRecipientAddress", isRecipientAddress)
                        .queryParam("limit", limit)
                        .queryParam("page", page)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("4xx Client Error: {}", body);
                                    return new BadRequestException("API 4xx error: " + body);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("5xx Server Error: {}", body);
                                    return new RuntimeException("API 5xx error: " + body);
                                })
                )
                .bodyToMono(String.class)
                .block();
        System.out.println("gelen data: "+responseJson);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseJson);
            boolean result = root.get("result").asBoolean();

            if (result){
                System.out.println(root);
                return mapper.treeToValue(root, AddressShipResponse.class);
            }else
                throw new BadRequestException("Kullanıcının kargo adresi oluşturulamadı.");

        } catch (JsonMappingException e) {
            throw new BadRequestException("Json datası eşleşmiyor.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json datası işlenemedi.");
        }
    }

    public String deleteShippingAddress(String id) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.delete()
                .uri("/addresses/%s".formatted(id))
                .header("Content-Type","application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("4xx Client Error: {}", body);
                                    return new BadRequestException("API 4xx error: " + body);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("5xx Server Error: {}", body);
                                    return new BadRequestException("API 5xx error: " + body);
                                })
                )
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);
            boolean result = root.get("result").asBoolean();

            if (result){
                JsonNode dataNode = root.get("data");
                AddressReceiptDto addressReceiptDto = mapper.treeToValue(dataNode, AddressReceiptDto.class);
                return "delete: "+ addressReceiptDto.getId();
            }else
                throw new BadRequestException("3. parti servisten silinemedi!");

        } catch (JsonMappingException e) {
            throw new BadRequestException("Json datası eşleşmiyor.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json datası işlenemedi.");
        }
    }

    public AddressReceiptDto getShippingAddress(String id) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.get()
                .uri("/addresses/%s".formatted(id))
                .header("Content-Type","application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("4xx Client Error: {}", body);
                                    return new BadRequestException("API 4xx error: " + body);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("5xx Server Error: {}", body);
                                    return new BadRequestException("API 5xx error: " + body);
                                })
                )
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);
            boolean result = root.get("result").asBoolean();

            if (result){
                JsonNode dataNode = root.get("data");
                return mapper.treeToValue(dataNode, AddressReceiptDto.class);
            }else
                throw new BadRequestException("3. parti servisten bukunamadı!");

        } catch (JsonMappingException e) {
            throw new BadRequestException("Json datası eşleşmiyor.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json datası işlenemedi.");
        }
    }
}
