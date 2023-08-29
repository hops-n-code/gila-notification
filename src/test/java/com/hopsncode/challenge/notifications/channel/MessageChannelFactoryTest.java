package com.hopsncode.challenge.notifications.channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageChannelFactoryTest {
    private MessageChannelFactory messageChannelFactory;

    @BeforeEach
    void setUp() {
        messageChannelFactory = new MessageChannelFactory() {
        };
    }

    @Test
    void defaultGetChannelForReturnsExpected() {
        assertThat(messageChannelFactory.getChannelFor(-1L))
                .satisfies(messageChannel -> assertThat(messageChannel.sendMessage(null)).isNull());
    }
}