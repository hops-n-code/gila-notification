package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailMessageChannelTest {
    @Test
    void sendMessageReturnsExpected() {
        EmailMessageChannel messageChannel = new EmailMessageChannel();

        Boolean result = messageChannel.sendMessage(MessageDTO.builder()
                .identifier("user@email.com")
                .message("The Message")
                .build());

        assertThat(result).isTrue();
    }
}