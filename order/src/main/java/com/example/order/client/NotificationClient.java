package com.example.order.client;


import com.example.order.dto.NotificationRequest;

import com.example.order.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NotificationClient {
    private final RestTemplate restTemplate;
    private static final String NOTIFICATION_SERVICE_URL = "http://localhost:8083/api/notifications/send";

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String processNotification(NotificationRequest request, String correlationId) {

        log.info("Calling Notification Service for user : {}", request.getUserId());


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Correlation-ID", correlationId);
        HttpEntity<NotificationRequest> entity = new HttpEntity<>(request,headers);

        return restTemplate.exchange(NOTIFICATION_SERVICE_URL, HttpMethod.POST, entity, String.class).getBody();

    }
}
