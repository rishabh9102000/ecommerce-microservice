package com.example.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestEvent {
    private String orderId ;
    private String userId ;
    private PaymentMode paymentMode ;
    private String senderAccount ;
    private String receiverAccount ;
    private double amount ;

}
