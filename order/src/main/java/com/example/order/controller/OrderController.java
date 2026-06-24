package com.example.order.controller;

import com.example.order.dto.OrderDto;
import com.example.order.model.Order;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody  OrderDto dto){
        String correlationId = UUID.randomUUID().toString();
        log.info("[correlationId={}] Received order request for productId: {}", correlationId, dto.getProductId());

        Order order = orderService.createOrder(dto,correlationId);
        return ResponseEntity.ok(order);

    }
}
