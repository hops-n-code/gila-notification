package com.hopsncode.challenge.notifications.api.notification.service;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import com.hopsncode.challenge.notifications.api.catalog.service.NotificationTypeCatalog;
import com.hopsncode.challenge.notifications.api.notification.model.Notification;
import com.hopsncode.challenge.notifications.api.user.model.User;
import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultNotificationMessageHelperTest {

    private static final String MESSAGE = "This is the message";

    private User johnDoe;
    private NotificationMessageHelper helper;

    @BeforeEach
    void setUp() {
        helper = new DefaultNotificationMessageHelper();

        johnDoe = User.builder()
                .email("user@example.com")
                .phone("1234567890")
                .name("John Doe")
                .build();
    }

    @Test
    void createMessageDTOFromSMSNotification() {
        Notification notification = Notification.builder()
                .type(Catalog.fromId(NotificationTypeCatalog.SMS))
                .user(johnDoe)
                .message(MESSAGE)
                .build();

        MessageDTO result = helper.createMessageDTOFromNotification(notification);

        assertThat(result.getIdentifier()).isEqualTo(johnDoe.getPhone());
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
    }

    @Test
    void createMessageDTOFromPushNotificationNotification() {
        Notification notification = Notification.builder()
                .type(Catalog.fromId(NotificationTypeCatalog.PUSH_NOTIFICATION))
                .user(johnDoe)
                .message(MESSAGE)
                .build();

        MessageDTO result = helper.createMessageDTOFromNotification(notification);

        assertThat(result.getIdentifier()).isEqualTo(johnDoe.getPhone());
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
    }

    @Test
    void createMessageDTOFromEmailNotification() {
        Notification notification = Notification.builder()
                .type(Catalog.fromId(NotificationTypeCatalog.EMAIL))
                .user(johnDoe)
                .message(MESSAGE)
                .build();

        MessageDTO result = helper.createMessageDTOFromNotification(notification);

        assertThat(result.getIdentifier()).isEqualTo(johnDoe.getEmail());
        assertThat(result.getMessage()).isEqualTo(MESSAGE);
    }

    @Test
    void createMessageDTOFromUnsupportedNotification() {
        Notification notification = Notification.builder()
                .type(Catalog.fromId(-1L))
                .user(johnDoe)
                .message(MESSAGE)
                .build();

        MessageDTO result = helper.createMessageDTOFromNotification(notification);

        assertThat(result.getIdentifier()).isNull();
        assertThat(result.getMessage()).isNull();
    }
}