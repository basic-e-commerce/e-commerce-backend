package com.example.ecommercebackend.service.product.shipping;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.*;
import com.example.ecommercebackend.entity.product.shipping.ShippingTemplate;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.product.shipping.ShippingTemplateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ShippingTemplateService {
    private final ShippingTemplateRepository shippingTemplateRepository;
    private final WebClient.Builder webClientBuilder;
    private String apiKey;

    public ShippingTemplateService(ShippingTemplateRepository shippingTemplateRepository, WebClient.Builder webClientBuilder) {
        this.shippingTemplateRepository = shippingTemplateRepository;
        this.webClientBuilder = webClientBuilder;
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.apiKey = dotenv.get("GELIVER");
    }


    public ShippingTemplate createShippingTemplate(@NotNullParam ShippingTemplateCreateDto shippingTemplateCreate) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.post()
                .uri("/parceltemplates")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(shippingTemplateCreate)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Response from API: " + responseJson);

        ObjectMapper mapper = new ObjectMapper();

        ShippingTemplateRequestDto block = null;
        try {
            block = mapper.readValue(responseJson, ShippingTemplateRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(e.getMessage());
        }


        ShippingTemplate shippingTemplate = new ShippingTemplate(
                block.getData().getId(),
                block.getData().getName(),
                block.getData().getLanguageCode(),
                block.getData().getLength(),
                block.getData().getWidth(),
                block.getData().getHeight(),
                block.getData().getDesi(),
                block.getData().getOldDesi(),
                block.getData().getDistanceUnit(),
                block.getData().getWeight(),
                block.getData().getOldWeight(),
                block.getData().getMassUnit(),
                block.getData().isActive(),
                Instant.parse(block.getData().getCreatedAt()),
                Instant.parse(block.getData().getUpdatedAt())
        );
        return shippingTemplateRepository.save(shippingTemplate);
    }


    public List<ShippingTemplateDto> getList(){
        return shippingTemplateRepository.findAll().stream().map(x-> {
            return new ShippingTemplateDto(
                    x.getGeliverId(),
                    x.getCreatedAt().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    x.getUpdatedAt().atZone(ZoneId.of("Europe/Istanbul")).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    x.getLength(),
                    x.getWidth(),
                    x.getHeight(),
                    x.getDesi(),
                    x.getOldDesi(),
                    x.getDistanceUnit(),
                    x.getWeight(),
                    x.getOldWeight(),
                    x.getMassUnit(),
                    x.getActive(),
                    x.getName(),
                    x.getLanguageCode()
            );
        }).toList();
    }

    public String deleteShippingTemplate(@NotNullParam String geliverId) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.delete()
                .uri("/parceltemplates/%s".formatted(geliverId))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        // responseJson: gelen JSON string
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = null;
        try {
            root = mapper.readTree(responseJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        boolean result = root.get("result").asBoolean();

        if (result) {
            System.out.println("Geliver Uygulamasından silindi");
            shippingTemplateRepository.deleteByGeliverId(geliverId);

            return "shippingTemplate veritabanından silindi";
        } else {
            System.out.println("Silme başarısız!");
            return "Silinemedi!";
        }

    }

    public BigDecimal getBalance() {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.delete()
                .uri("/organizations/2340f7f2-1bf7-4c5b-b4cd-1244c4a1cf3a/balance")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // responseJson: gelen JSON string
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = null;
        try {
            root = mapper.readTree(responseJson);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("3. parti yazılımdan gelen data çözülemedi.");
        }

        boolean result = root.get("result").asBoolean();

        if (result) {
            return new BigDecimal(root.get("data").asText());
        }else
            throw new BadRequestException("Geçersiz istek lütfen tekrar deneyiniz!");
    }

    public List<CargoOffer> getCargoOffer(@NotNullParam String length,
                                          @NotNullParam String width,
                                          @NotNullParam String height,
                                          @NotNullParam String weight){
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/priceList")
                        .queryParam("paramType", "parcel")
                        .queryParam("length", length)
                        .queryParam("width", width)
                        .queryParam("height", height)
                        .queryParam("weight", weight)
                        .build())
                .header("Authorization","Bearer %s".formatted(apiKey))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // responseJson: gelen JSON string
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String additionalMessage = root.get("additionalMessage").asText();

            if (result && "Success".equals(additionalMessage)) {
                JsonNode offersNode = root
                        .path("priceList")
                        .get(0)      // ilk priceList elemanı
                        .path("offers");

                // JsonNode --> List<CargoOffer>
                return mapper.readerForListOf(CargoOffer.class).readValue(offersNode);
            } else {
                throw new RuntimeException("API response unsuccessful: " + additionalMessage);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse API response", e);
        }


    }

}
