package com.example.notification.strategy;

import com.example.notification.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsNotificationStrategy implements NotificationStrategy{
    @Override
    public void send(NotificationRequest request) {
        log.info("Sending SMS to user {}: {}", request.getUserId(), request.getMessage());
    }
}
