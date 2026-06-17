package com.example.order.client;

import com.example.order.dto.InventoryCheckResponse;
import lombok.extern.slf4j.Slf4j;
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

    public InventoryCheckResponse checkAvailability(String productId, int quantity) {
        String url = String.format("%s?productId=%s&quantity=%d", INVENTORY_SERVICE_URL, productId, quantity);
        log.info("Calling Inventory Service for productId: {}", productId);

        InventoryCheckResponse response = restTemplate.getForObject(url, InventoryCheckResponse.class);

        log.info("Inventory Service responded: {}", response);
        return response;
    }
}