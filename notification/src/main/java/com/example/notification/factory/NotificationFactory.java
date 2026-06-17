package com.example.notification.factory;

import com.example.notification.model.NotificationType;
import com.example.notification.strategy.EmailNotificationStrategy;
import com.example.notification.strategy.NotificationStrategy;
import com.example.notification.strategy.SmsNotificationStrategy;

public class NotificationFactory {
    public static NotificationStrategy getStrategy(NotificationType type) {
        return switch (type) {
            case EMAIL -> new EmailNotificationStrategy();
            case SMS -> new SmsNotificationStrategy();
        };
    }
}
