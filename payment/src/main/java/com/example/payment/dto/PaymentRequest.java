package com.example.payment.dto;


import com.example.payment.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String orderId ;
    private String userId ;
    private PaymentMode paymentMode ;
    private String senderAccount ;
    private String receiverAccount ;
    private double amount ;
}
