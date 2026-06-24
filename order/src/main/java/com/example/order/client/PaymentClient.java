package com.example.order.client;


import com.example.order.dto.InventoryCheckResponse;
import com.example.order.dto.PaymentRequest;
import com.example.order.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PaymentClient {

    private final RestTemplate restTemplate;
    private static final String PAYMENT_SERVICE_URL = "http://localhost:8082/api/payment";

    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentResponse processPayment(PaymentRequest request, String correlationId) {

        log.info("Calling Payment Service for payment: {}", request.getUserId());

//        PaymentResponse response = restTemplate.postForObject(PAYMENT_SERVICE_URL,request,PaymentResponse.class);
//
//        log.info("Payment Service responded: {}", response);
//        return response;
//        PaymentResponse response = restTemplate.postForObject(PAYMENT_SERVICE_URL,request,PaymentResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Correlation-ID", correlationId);
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request,headers);

        return restTemplate.exchange(PAYMENT_SERVICE_URL, HttpMethod.POST, entity, PaymentResponse.class).getBody();
    }
}
