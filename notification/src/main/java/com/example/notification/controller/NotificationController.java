package com.example.notification.controller;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/api/notifications/send")
    public ResponseEntity<String>  sendNotification(@RequestBody NotificationRequest request,
                                                    @RequestHeader(value = "X-Correlation-ID", required = false) String correlationId){

        log.info("[correlationId={}] Request received to send notification for userId: {}", correlationId, request.getUserId());

        service.sendNotification(request);
        return ResponseEntity.ok("Notification Sent Successfully");
    }
}
