package com.hopsncode.challenge.notifications.api.notification.service;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import com.hopsncode.challenge.notifications.api.catalog.service.NotificationStatusCatalog;
import com.hopsncode.challenge.notifications.api.catalog.service.NotificationTypeCatalog;
import com.hopsncode.challenge.notifications.api.notification.dto.MessageRequestDTO;
import com.hopsncode.challenge.notifications.api.notification.dto.NotificationSummaryDTO;
import com.hopsncode.challenge.notifications.api.notification.model.Notification;
import com.hopsncode.challenge.notifications.api.notification.repository.NotificationRepository;
import com.hopsncode.challenge.notifications.api.user.dto.UserDTO;
import com.hopsncode.challenge.notifications.api.user.model.User;
import com.hopsncode.challenge.notifications.api.user.repository.UserRepository;
import com.hopsncode.challenge.notifications.channel.DefaultMessageChannelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    private static final long MESSAGE_CATEGORY_ID = 100001L;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private NotificationRepository mockNotificationRepository;
    @Mock
    private NotificationMessageHelper mockNotificationMessageHelper;
    @Mock
    private DefaultMessageChannelFactory mockMessageChannelFactory;

    @InjectMocks
    private NotificationService service;
    private MessageRequestDTO messageRequest;
    private User.UserBuilder johnDoeBuilder;

    @BeforeEach
    void setUp() {
        messageRequest = MessageRequestDTO.builder()
                .categoryId(MESSAGE_CATEGORY_ID)
                .message("This is the message")
                .build();
        johnDoeBuilder = User.builder()
                .email("user@example.com")
                .phone("1234567890")
                .name("John Doe");
    }

    @Test
    void sendNotificationReturnsExpectedWhenNoUsersSubscribedToCategory() {
        when(mockUserRepository.findByMessageCategory(MESSAGE_CATEGORY_ID)).thenReturn(List.of());

        NotificationSummaryDTO summaryDTO = service.sendNotificationsForMessage(messageRequest);

        assertThat(summaryDTO)
                .extracting(NotificationSummaryDTO::getTotal)
                .isEqualTo(0);
    }

    @Test
    void sendNotificationReturnsExpectedWhenNoSupportedChannelsAvailable() {
        long unsupportedNotificationType = -1L;

        when(mockUserRepository.findByMessageCategory(MESSAGE_CATEGORY_ID))
                .thenReturn(List.of(
                        johnDoeBuilder
                                .notificationTypes(Set.of(Catalog.fromId(unsupportedNotificationType)))
                                .build()
                ));

        when(mockMessageChannelFactory.getChannelFor(unsupportedNotificationType))
                .thenReturn(messageDTO -> null);

        NotificationSummaryDTO summaryDTO = service.sendNotificationsForMessage(messageRequest);

        assertThat(summaryDTO.getTotal()).isEqualTo(1);
        assertThat(summaryDTO.getSent()).isEqualTo(0);
        assertThat(summaryDTO.getFailed()).isEqualTo(0);
        assertThat(summaryDTO.getPending()).isEqualTo(1);
    }

    @Test
    void sendNotificationReturnsExpectedWhenSupportedChannelAvailable() {
        when(mockUserRepository.findByMessageCategory(MESSAGE_CATEGORY_ID))
                .thenReturn(List.of(
                        johnDoeBuilder
                                .notificationTypes(Set.of(
                                        Catalog.fromId(NotificationTypeCatalog.SMS),
                                        Catalog.fromId(NotificationTypeCatalog.PUSH_NOTIFICATION)
                                ))
                                .build()
                ));

        when(mockMessageChannelFactory.getChannelFor(anyLong()))
                .thenReturn(messageDTO -> true, messageDTO -> false);

        NotificationSummaryDTO summaryDTO = service.sendNotificationsForMessage(messageRequest);

        assertThat(summaryDTO.getTotal()).isEqualTo(2);
        assertThat(summaryDTO.getSent()).isEqualTo(1);
        assertThat(summaryDTO.getFailed()).isEqualTo(1);
        assertThat(summaryDTO.getPending()).isEqualTo(0);
    }

    @Test
    void getSentNotificationsReturnsExpected() {
        User johnDoe = johnDoeBuilder.build();
        when(mockNotificationRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(
                        Notification.builder()
                                .id(1L)
                                .createDateTime(LocalDateTime.now())
                                .type(Catalog.fromId(NotificationTypeCatalog.PUSH_NOTIFICATION))
                                .messageCategory(Catalog.fromId(MESSAGE_CATEGORY_ID))
                                .status(Catalog.fromId(NotificationStatusCatalog.SENT))
                                .message(messageRequest.getMessage())
                                .user(johnDoe)
                                .build()
                ));

        assertThat(service.getSentNotifications())
                .hasSize(1)
                .element(0)
                .satisfies(sentNotificationDTO -> {
                    assertThat(sentNotificationDTO.getId()).isEqualTo(1L);
                    assertThat(sentNotificationDTO.getTypeId()).isEqualTo(NotificationTypeCatalog.PUSH_NOTIFICATION);
                    assertThat(sentNotificationDTO.getMessage()).isEqualTo(messageRequest.getMessage());
                    assertThat(sentNotificationDTO.getUser())
                            .extracting(UserDTO::getName, UserDTO::getEmail, UserDTO::getPhone)
                            .containsAll(List.of(johnDoe.getName(), johnDoe.getEmail(), johnDoe.getPhone()));
                });
    }
}