package com.example.order.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Builder.Default
    private String orderId = UUID.randomUUID().toString();
    private  String productId;
    private Status status;
    private String userId;
    private int quantity;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();



}


