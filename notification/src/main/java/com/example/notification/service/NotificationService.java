package com.example.notification.service;

import com.example.notification.dto.NotificationRequest;

public interface NotificationService {

    public void sendNotification(NotificationRequest request);
}
