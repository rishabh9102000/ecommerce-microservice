package com.example.order.service;

import com.example.order.client.InventoryClient;
import com.example.order.client.NotificationClient;
import com.example.order.client.PaymentClient;
import com.example.order.dto.*;
import com.example.order.exception.OrderException;
import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;

    public OrderServiceImpl(InventoryClient inventoryClient, PaymentClient paymentClient, NotificationClient notificationClient) {
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
        this.notificationClient = notificationClient;
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
        try {

            InventoryCheckResponse response = inventoryClient.checkAvailability(dto.getProductId(), dto.getQuantity());
            PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);
            if (response.isAvailable() && paymentResponse.getStatus() == PaymentStatus.SUCCESS) {
                order.setStatus(OrderStatus.CONFIRMED);
                log.info("Notification response: {}", notificationClient.processNotification(notificationRequest));
            }


        } catch (HttpClientErrorException ex) {

            order.setStatus(OrderStatus.FAILED);

            throw new OrderException("Order not accepted for user :" + dto.getUserId());

        }
        return order;
    }
}
