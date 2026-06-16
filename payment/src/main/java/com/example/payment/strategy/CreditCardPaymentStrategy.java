package com.example.payment.strategy;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.exception.PaymentFailedException;
import com.example.payment.model.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Using Credit for payment");
        boolean isSuccess = new Random().nextInt(100) < 70;
        PaymentResponse response =null;
        if (isSuccess) {
            response= PaymentResponse.builder()
                    .amount(request.getAmount())
                    .status(Status.SUCCESS)
                    .message("Credit payment success")
                    .build();
            // return SUCCESS response
            log.info("Credit payment successful for orderId: {}", request.getOrderId());

        } else {
            log.warn("Credit payment failed for orderId: {}", request.getOrderId());

            throw new PaymentFailedException("Credit payment failed for orderId: " + request.getOrderId());

            // throw PaymentFailedException
        }
        return  response;
    }
}
