package com.example.order.client;


import com.example.order.dto.PaymentRequest;
import com.example.order.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
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

    public PaymentResponse processPayment(PaymentRequest request) {

        log.info("Calling Payment Service for payment: {}", request.getUserId());

        PaymentResponse response = restTemplate.postForObject(PAYMENT_SERVICE_URL,request,PaymentResponse.class);

        log.info("Payment Service responded: {}", response);
        return response;
    }
}
