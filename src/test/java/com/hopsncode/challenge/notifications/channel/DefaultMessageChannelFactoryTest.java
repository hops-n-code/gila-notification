package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.api.catalog.service.NotificationTypeCatalog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DefaultMessageChannelFactoryTest {

    @Mock
    private SmsMessageChannel mockSmsMessageChannel;
    @Mock
    private PushNotificationMessageChannel mockPushNotificationMessageChannel;
    @Mock
    private EmailMessageChannel mockEmailMessageChannel;

    @InjectMocks
    private DefaultMessageChannelFactory messageChannelFactory;

    @Test
    void getChannelForReturnsExpectedWhenMappingExists() {
        assertThat(messageChannelFactory.getChannelFor(NotificationTypeCatalog.SMS))
                .isExactlyInstanceOf(SmsMessageChannel.class);

        assertThat(messageChannelFactory.getChannelFor(NotificationTypeCatalog.PUSH_NOTIFICATION))
                .isExactlyInstanceOf(PushNotificationMessageChannel.class);

        assertThat(messageChannelFactory.getChannelFor(NotificationTypeCatalog.EMAIL))
                .isExactlyInstanceOf(EmailMessageChannel.class);
    }

    @Test
    void getChannelForThrowsExceptionWhenMappingNotExists() {

        assertThat(messageChannelFactory.getChannelFor(-1L))
                .satisfies(messageChannel -> assertThat(messageChannel.sendMessage(null)).isNull());
    }
}