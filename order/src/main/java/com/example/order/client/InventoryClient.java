package com.example.order.client;

import com.example.order.dto.InventoryCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Slf4j
@Component
public class InventoryClient {

    private final RestTemplate restTemplate;
    private static final String INVENTORY_SERVICE_URL = "http://localhost:8081/api/inventory/check";

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventoryCheckResponse checkAvailability(String productId, int quantity ,String correlationId) {
        String url = String.format("%s?productId=%s&quantity=%d", INVENTORY_SERVICE_URL, productId, quantity);
        log.info("Calling Inventory Service for productId: {}", productId);


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Correlation-ID", correlationId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, InventoryCheckResponse.class).getBody();
    }
}