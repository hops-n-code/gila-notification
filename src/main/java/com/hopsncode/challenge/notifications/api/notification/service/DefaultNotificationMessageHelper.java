package com.hopsncode.challenge.notifications.api.notification.service;

import com.hopsncode.challenge.notifications.api.catalog.service.NotificationTypeCatalog;
import com.hopsncode.challenge.notifications.api.notification.model.Notification;
import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class DefaultNotificationMessageHelper implements NotificationMessageHelper {

    private final Map<Long, Function<Notification, MessageDTO>> notificationConverterMap = Map.of(
            NotificationTypeCatalog.SMS, notification -> MessageDTO.builder()
                    .identifier(notification.getUser().getPhone())
                    .message(notification.getMessage())
                    .build(),
            NotificationTypeCatalog.EMAIL, notification -> MessageDTO.builder()
                    .identifier(notification.getUser().getEmail())
                    .message(notification.getMessage())
                    .build(),
            NotificationTypeCatalog.PUSH_NOTIFICATION, notification -> MessageDTO.builder()
                    .identifier(notification.getUser().getPhone())
                    .message(notification.getMessage())
                    .build()
    );

    @Override
    public MessageDTO createMessageDTOFromNotification(Notification notification) {
        return notificationConverterMap.getOrDefault(
                        notification.getType().getId(),
                        n -> MessageDTO.builder().build())
                .apply(notification);
    }
}
