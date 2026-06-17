package com.example.order.client;


import com.example.order.dto.NotificationRequest;

import lombok.extern.slf4j.Slf4j;
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

    public String processNotification(NotificationRequest request) {

        log.info("Calling Notification Service for user : {}", request.getUserId());

       String response = restTemplate.postForObject(NOTIFICATION_SERVICE_URL,request,String.class);

        log.info("Notification Service responded: {}", response);

        return response;

    }
}
