package com.example.notification.service;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.factory.NotificationFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class NotificationServiceImpl implements  NotificationService{



    @Override
    public void sendNotification(NotificationRequest request) {
        log.info("Sending {} notification to userId: {}", request.getType(), request.getUserId());
        NotificationFactory.getStrategy(request.getType()).send(request);
    }
}
