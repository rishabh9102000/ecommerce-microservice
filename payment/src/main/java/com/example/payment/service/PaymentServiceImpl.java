package com.example.payment.service;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.factory.PaymentFactory;
import com.example.payment.strategy.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{



    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for orderId: {}", request.getOrderId());
        PaymentStrategy strategy = PaymentFactory.getStrategy(request.getPaymentMode());
        PaymentResponse response = strategy.processPayment(request);
        log.info("Payment completed for orderId: {} with status: {}", request.getOrderId(), response.getStatus());
        return response;
    }
}
