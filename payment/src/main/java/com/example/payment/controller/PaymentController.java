package com.example.payment.controller;


import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {

    private final PaymentService service ;

    public PaymentController(PaymentService service){
        this.service = service;
    }

    @PostMapping("/api/payment")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request){
        log.info("Request recieved for payment");
        PaymentResponse response = service.processPayment(request);
        return ResponseEntity.ok(response);
    }
}
