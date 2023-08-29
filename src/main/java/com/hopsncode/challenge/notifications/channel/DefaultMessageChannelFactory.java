package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.api.catalog.service.NotificationTypeCatalog;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class DefaultMessageChannelFactory implements MessageChannelFactory {

    private final Map<Long, MessageChannel> messageChannelMap;

    public DefaultMessageChannelFactory(SmsMessageChannel smsMessageChannel,
                                        PushNotificationMessageChannel pushNotificationMessageChannel,
                                        EmailMessageChannel emailMessageChannel) {
        this.messageChannelMap = Map.of(
                NotificationTypeCatalog.SMS, smsMessageChannel,
                NotificationTypeCatalog.PUSH_NOTIFICATION, pushNotificationMessageChannel,
                NotificationTypeCatalog.EMAIL, emailMessageChannel
        );
    }

    @Override
    public MessageChannel getChannelFor(Long notificationTypeId) {
        return Optional.ofNullable(messageChannelMap.get(notificationTypeId))
                .orElse(messageDTO -> null);
    }
}
