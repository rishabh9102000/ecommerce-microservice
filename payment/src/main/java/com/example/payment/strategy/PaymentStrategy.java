package com.example.payment.strategy;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;

public interface PaymentStrategy {
    PaymentResponse processPayment(PaymentRequest request);
}
