package com.example.payment.dto;

import com.example.payment.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    @Builder.Default
    private String transactionId = UUID.randomUUID().toString();
    private Status status;
    private String message;
    private double amount;
}
