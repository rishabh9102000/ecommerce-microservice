package com.example.order.service;

import com.example.order.client.InventoryClient;

import com.example.order.dto.*;
import com.example.order.exception.OrderException;
import com.example.order.kafka.PaymentEventProducer;
import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final InventoryClient inventoryClient;

    private final ExecutorService orderProcessingExecutor;
    private final PaymentEventProducer paymentEventProducer;

    public OrderServiceImpl(InventoryClient inventoryClient,PaymentEventProducer paymentEventProducer,

                            @Qualifier("orderProcessingExecutor")ExecutorService orderProcessingExecutor) {
        this.inventoryClient = inventoryClient;

        this.orderProcessingExecutor = orderProcessingExecutor;
        this.paymentEventProducer = paymentEventProducer;
    }

    @Override
    public Order createOrder(OrderDto dto,String correlationId) {
        Order order = Order.builder()
                .productId(dto.getProductId())
                .status(OrderStatus.CREATED)
                .quantity(dto.getQuantity())
                .userId(dto.getUserId())
                .build();
        log.info("[correlationId={}] Order created with orderId: {}", correlationId, order.getOrderId());


        PaymentRequestEvent paymentRequestEvent = PaymentRequestEvent.builder()
                .orderId(order.getOrderId())
                .userId(dto.getUserId())
                .paymentMode(PaymentMode.UPI)
                .senderAccount("rishabh")
                .receiverAccount("rishu")
                .amount(500.0)
                .build();




        CompletableFuture<Void> orderFuture = CompletableFuture
                .supplyAsync(() -> {

                    log.info("[correlationId={}] Inventory Running on thread: {}", correlationId, Thread.currentThread().getName());
                    return inventoryClient.checkAvailability(dto.getProductId(), dto.getQuantity(),correlationId);
                }, orderProcessingExecutor)
                .thenAcceptAsync(inventoryResponse -> {
                    log.info("[correlationId={}] Publishing payment event on thread: {}", correlationId, Thread.currentThread().getName());
                    // call paymentClient here, return paymentResponse
                    paymentEventProducer.publishPaymentRequest(paymentRequestEvent,correlationId);
                    order.setStatus(OrderStatus.CONFIRMED);

                }, orderProcessingExecutor)

                .exceptionally(ex -> {
                    // update order status to FAILED
                    // throw OrderException
                    log.error("[correlationId={}] Order failed with exception: {}", correlationId, ex.getMessage(), ex);
                    order.setStatus(OrderStatus.FAILED);
                    throw new OrderException("Order not accepted for user :" + dto.getUserId());
                });

        // blocks until chain completes, then returns order
        try {
            orderFuture.join();
        } catch (CompletionException ex) {
            throw new OrderException("Order not accepted for user: " + dto.getUserId());
        }
        return order;

    }
}
