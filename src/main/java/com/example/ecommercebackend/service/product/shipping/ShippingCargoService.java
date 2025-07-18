package com.example.ecommercebackend.service.product.shipping;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.*;
import com.example.ecommercebackend.exception.BadRequestException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ShippingCargoService {

    private static final Logger log = LoggerFactory.getLogger(ShippingCargoService.class);
    private final WebClient.Builder webClientBuilder;
    private final String apiKey;

    public ShippingCargoService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        Dotenv dotenv = Dotenv.load(); // .env dosyasını otomatik bulur
        this.apiKey = dotenv.get("GELIVER");
    }

    /*
        {
          "result": false,
          "code": "E1068",
          "message": "Gönderici adresi bulunamadı",
          "additionalMessage": "Sender address record not found"
        }
     */

    public CargoOfferResponseDto getCreateCargoOffers(@NotNullParam CargoOfferRequestDto cargoOfferRequestDto){
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();
        String responseJson = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/shipments")
                        .build())
                .header("Authorization","Bearer %s".formatted(apiKey))
                .header("Content-Type","application/json")
                .bodyValue(cargoOfferRequestDto)
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

        // responseJson: gelen JSON string
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String additionalMessage = root.get("additionalMessage").asText();

            if (result) {
                return mapper.readValue(responseJson, CargoOfferResponseDto.class);
            } else {
                throw new BadRequestException("API response unsuccessful: " + additionalMessage);
            }

        } catch (Exception e) {
            throw new BadRequestException("Failed to parse API response " + e.getMessage());
        }


    }

    public OfferApproveDto offerApprove(@NotNullParam String offerId) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        // Request Body
        Map<String, String> body = Map.of("offerID", offerId);

        String responseJson = webClient.post()
                .uri("/transactions")
                .header("Authorization", "Bearer %s".formatted(apiKey))
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(resp -> {
                                    log.error("4xx Client Error: {}", resp);
                                    return new BadRequestException("API 4xx error: " + resp);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(resp -> {
                                    log.error("5xx Server Error: {}", resp);
                                    return new BadRequestException("API 5xx error: " + resp);
                                })
                )
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String message = root.get("message").asText();

            if (result) {
                return mapper.readValue(responseJson, OfferApproveDto.class);
            } else {
                throw new BadRequestException("API response unsuccessful: " + message);
            }

        } catch (Exception e) {
            throw new BadRequestException("Failed to parse API response: " + e.getMessage());
        }
    }


    public OfferCancelDto offerCancel(@NotNullParam String id) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.delete()
                .uri("/shipments/%s".formatted(id))
                .header("Authorization", "Bearer %s".formatted(apiKey))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(resp -> {
                                    log.error("4xx Client Error: {}", resp);
                                    return new BadRequestException("API 4xx error: " + resp);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(resp -> {
                                    log.error("5xx Server Error: {}", resp);
                                    return new BadRequestException("API 5xx error: " + resp);
                                })
                )
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String message = root.get("additionalMessage").asText();

            if (!result) {
                throw new BadRequestException("Failed to delete shipment: " + message);
            }

            log.info("Shipment {} deleted successfully: {}", id, message);
            return mapper.readValue(responseJson, OfferCancelDto.class);

        } catch (Exception e) {
            throw new BadRequestException("Failed to parse API response: " + e.getMessage());
        }
    }

    public CargoDetailDto getCargoDetail(@NotNullParam String id){
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.get()
                .uri("/shipments/%s".formatted(id))
                .header("Authorization", "Bearer %s".formatted(apiKey))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(resp -> {
                                    log.error("4xx Client Error: {}", resp);
                                    return new BadRequestException("API 4xx error: " + resp);
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(resp -> {
                                    log.error("5xx Server Error: {}", resp);
                                    return new BadRequestException("API 5xx error: " + resp);
                                })
                )
                .bodyToMono(String.class)
                .block();
        System.out.println(responseJson);

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(responseJson);

            boolean result = root.get("result").asBoolean();
            String message = root.get("additionalMessage").asText();

            if (!result) {
                throw new BadRequestException("Failed to getdetail shipment: " + message);
            }

            log.info("Shipment {} get successfully: {}", id, message);
            return mapper.readValue(responseJson, CargoDetailDto.class);

        } catch (Exception e) {
            throw new BadRequestException("Failed to parse API response: " + e.getMessage());
        }

    }

    public ShipmentsListResponseDto listShipments(
            Integer limit,
            Integer page,
            String sortBy,
            String filter,
            String startDate,
            String endDate,
            String statusFilter,
            String invoiceID,
            String providerServiceCode,
            String storeIdentifier,
            Boolean isReturned
    ) {
        WebClient webClient = webClientBuilder.baseUrl("https://api.geliver.io/api/v1").build();

        String responseJson = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/shipments")
                        .queryParam("limit", limit != null ? limit : 25)
                        .queryParam("page", page != null ? page : 1)
                        .queryParam("sortBy", sortBy != null ? sortBy : "organization_shipment_id desc")
                        .queryParam("filter", filter != null ? filter : "")
                        .queryParam("startDate", startDate != null ? startDate : "")
                        .queryParam("endDate", endDate != null ? endDate : "")
                        .queryParam("statusFilter", statusFilter != null ? statusFilter : "")
                        .queryParam("invoiceID", invoiceID != null ? invoiceID : "")
                        .queryParam("providerServiceCode", providerServiceCode != null ? providerServiceCode : "")
                        .queryParam("storeIdentifier", storeIdentifier != null ? storeIdentifier : "")
                        .queryParam("isReturned", isReturned != null ? isReturned : false)
                        .build())
                .header("Authorization", "Bearer %s".formatted(apiKey))
                .header("Content-Type", "application/json")
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

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseJson, ShipmentsListResponseDto.class);
        } catch (Exception e) {
            throw new BadRequestException("Failed to parse API response: " + e.getMessage());
        }
    }



}
