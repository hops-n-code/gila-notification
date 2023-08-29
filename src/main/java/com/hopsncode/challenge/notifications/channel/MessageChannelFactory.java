package com.hopsncode.challenge.notifications.channel;

public interface MessageChannelFactory {
    default MessageChannel getChannelFor(Long notificationTypeId) {
        return messageDTO -> null;
    }
}
