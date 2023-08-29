package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class SmsMessageChannelTest {
    @Test
    void sendMessageReturnsExpected() {
        SmsMessageChannel messageChannel = new SmsMessageChannel();

        Boolean result = messageChannel.sendMessage(MessageDTO.builder()
                .identifier("1234567890")
                .message("The Message")
                .build());

        assertThat(result).isTrue();
    }

    @Test
    void sendMessageReturnsExpectedWhenBackoffDisabled() {
        SmsMessageChannel messageChannel = new SmsMessageChannel();
        ReflectionTestUtils.setField(messageChannel, "useExponentialBackOff", false);

        Boolean result = messageChannel.sendMessage(MessageDTO.builder()
                .identifier("1234567890")
                .message("The Message")
                .build());

        assertThat(result).isTrue();
    }
}