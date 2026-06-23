package com.example.order.service;

import com.example.order.client.InventoryClient;
import com.example.order.client.NotificationClient;
import com.example.order.client.PaymentClient;
import com.example.order.dto.*;
import com.example.order.exception.OrderException;
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
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;
    private final ExecutorService orderProcessingExecutor;

    public OrderServiceImpl(InventoryClient inventoryClient, PaymentClient paymentClient,
                            NotificationClient notificationClient,
                            @Qualifier("orderProcessingExecutor")ExecutorService orderProcessingExecutor) {
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
        this.notificationClient = notificationClient;
        this.orderProcessingExecutor = orderProcessingExecutor;
    }

    @Override
    public Order createOrder(OrderDto dto) {
        Order order = Order.builder()
                .productId(dto.getProductId())
                .status(OrderStatus.CREATED)
                .quantity(dto.getQuantity())
                .userId(dto.getUserId())
                .build();
        log.info("Order created with orderid : {}" ,order.getOrderId());
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .userId(dto.getUserId())
                .paymentMode(PaymentMode.UPI)
                .senderAccount("rishabh")
                .receiverAccount("rishu")
                .amount(500.0)
                .build();

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(dto.getUserId())
                .message("Order Placed successfully with orderid :  " + order.getOrderId())
                .type(NotificationType.SMS)
                .build();

        CompletableFuture<Void> orderFuture = CompletableFuture
                .supplyAsync(() -> {
                    log.info("Inventory Running on thread: {}", Thread.currentThread().getName());
                    return inventoryClient.checkAvailability(dto.getProductId(), dto.getQuantity());
                }, orderProcessingExecutor)
                .thenApplyAsync(inventoryResponse -> {
                    log.info("Payment Running on thread: {}", Thread.currentThread().getName());
                    // call paymentClient here, return paymentResponse
                    return paymentClient.processPayment(paymentRequest);
                }, orderProcessingExecutor)
                .thenAcceptAsync(paymentResponse -> {
                    // call notificationClient here
                    log.info(" notification Running on thread: {}", Thread.currentThread().getName());
                    order.setStatus(OrderStatus.CONFIRMED);
                    log.info("Notification response: {}", notificationClient.processNotification(notificationRequest));
                    // update order status to CONFIRMED
                }, orderProcessingExecutor)
                .exceptionally(ex -> {
                    // update order status to FAILED
                    // throw OrderException
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
