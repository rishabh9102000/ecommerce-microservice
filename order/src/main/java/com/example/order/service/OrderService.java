package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.model.Order;
import org.springframework.stereotype.Service;

public interface OrderService {

    public Order createOrder(OrderDto dto , String correlationID);
}
