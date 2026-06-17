package com.example.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaymentResponse {

    private String transactionId;
    private PaymentStatus status;
    private String message;
    private double amount;
}
