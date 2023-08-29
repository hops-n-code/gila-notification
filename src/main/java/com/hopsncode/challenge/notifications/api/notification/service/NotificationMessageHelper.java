package com.hopsncode.challenge.notifications.api.notification.service;

import com.hopsncode.challenge.notifications.api.notification.model.Notification;
import com.hopsncode.challenge.notifications.common.dto.MessageDTO;

public interface NotificationMessageHelper {
    MessageDTO createMessageDTOFromNotification(Notification notification);
}
