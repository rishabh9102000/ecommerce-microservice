package com.example.notification.controller;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/api/notifications/send")
    public ResponseEntity<String>  sendNotification(@RequestBody NotificationRequest request){
        log.info("Received notification request for userId: {}", request.getUserId());
        service.sendNotification(request);
        return ResponseEntity.ok("Notification Sent Successfully");
    }
}
