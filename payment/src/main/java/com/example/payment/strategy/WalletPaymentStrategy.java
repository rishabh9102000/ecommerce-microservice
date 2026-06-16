package com.example.payment.strategy;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.exception.PaymentFailedException;
import com.example.payment.model.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class WalletPaymentStrategy implements PaymentStrategy{
    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Using Wallet for payment");
        boolean isSuccess = new Random().nextInt(100) < 70;
        PaymentResponse response =null;
        if (isSuccess) {
            response= PaymentResponse.builder()
                    .amount(request.getAmount())
                    .status(Status.SUCCESS)
                    .message("Wallet payment success")
                    .build();
            // return SUCCESS response
            log.info("Wallet payment successful for orderId: {}", request.getOrderId());

        } else {
            log.warn("Wallet payment failed for orderId: {}", request.getOrderId());
            throw new PaymentFailedException("Wallet payment failed for orderId: " + request.getOrderId());


            // throw PaymentFailedException
        }
        return  response;
    }
}
