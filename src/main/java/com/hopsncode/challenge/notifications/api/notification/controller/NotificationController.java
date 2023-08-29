package com.hopsncode.challenge.notifications.api.notification.controller;

import com.hopsncode.challenge.notifications.api.notification.dto.MessageRequestDTO;
import com.hopsncode.challenge.notifications.api.notification.dto.SentNotificationDTO;
import com.hopsncode.challenge.notifications.api.notification.service.NotificationService;
import com.hopsncode.challenge.notifications.common.dto.SimpleResponse;
import com.hopsncode.challenge.notifications.common.validation.group.OnSendNotification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Validated
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @Validated(OnSendNotification.class)
    public ResponseEntity<SimpleResponse> sendNotification(@Valid @RequestBody MessageRequestDTO messageRequestDTO) {

        return ResponseEntity.ok(new SimpleResponse(notificationService.sendNotificationsForMessage(messageRequestDTO)));
    }

    @GetMapping
    public ResponseEntity<List<SentNotificationDTO>> getSentNotifications() {
        return ResponseEntity.ok(notificationService.getSentNotifications());
    }
}
