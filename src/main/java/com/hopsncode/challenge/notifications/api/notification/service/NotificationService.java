package com.hopsncode.challenge.notifications.api.notification.service;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import com.hopsncode.challenge.notifications.api.catalog.service.NotificationStatusCatalog;
import com.hopsncode.challenge.notifications.api.notification.dto.MessageRequestDTO;
import com.hopsncode.challenge.notifications.api.notification.dto.NotificationSummaryDTO;
import com.hopsncode.challenge.notifications.api.notification.dto.SentNotificationDTO;
import com.hopsncode.challenge.notifications.api.notification.model.Notification;
import com.hopsncode.challenge.notifications.api.notification.repository.NotificationRepository;
import com.hopsncode.challenge.notifications.api.user.dto.UserDTO;
import com.hopsncode.challenge.notifications.api.user.repository.UserRepository;
import com.hopsncode.challenge.notifications.channel.MessageChannel;
import com.hopsncode.challenge.notifications.channel.MessageChannelFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final MessageChannelFactory messageChannelFactory;
    private final NotificationMessageHelper notificationMessageHelper;

    @Transactional
    public NotificationSummaryDTO sendNotificationsForMessage(MessageRequestDTO messageRequestDTO) {
        Long categoryId = messageRequestDTO.getCategoryId();

        List<Notification> notifications = userRepository.findByMessageCategory(categoryId).stream()
                .flatMap(user -> user.getNotificationTypes().stream()
                        .map(notificationType -> Notification.builder()
                                .status(Catalog.fromId(NotificationStatusCatalog.PENDING))
                                .type(notificationType)
                                .messageCategory(Catalog.fromId(categoryId))
                                .user(user)
                                .message(messageRequestDTO.getMessage())
                                .build()))
                .toList();

        notificationRepository.saveAll(notifications);

        NotificationSummaryDTO summary = new NotificationSummaryDTO(notifications.size());

        for (Notification notification : notifications) {
            MessageChannel messageChannel = messageChannelFactory.getChannelFor(notification.getType().getId());

            Boolean result = messageChannel.sendMessage(notificationMessageHelper.createMessageDTOFromNotification(notification));

            Optional.ofNullable(result).ifPresent(success -> {
                if (success) {
                    summary.recordSent();
                    notification.updateStatus(NotificationStatusCatalog.SENT);
                } else {
                    summary.recordFailed();
                    notification.updateStatus(NotificationStatusCatalog.FAILED);
                }
                notificationRepository.save(notification);
            });
        }

        return summary;
    }

    public List<SentNotificationDTO> getSentNotifications() {
        return notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createDateTime")).stream()
                .map(notification -> SentNotificationDTO.builder()
                        .id(notification.getId())
                        .createdDateTime(notification.getCreateDateTime())
                        .typeId(notification.getType().getId())
                        .typeDesc(notification.getType().getOption())
                        .categoryDesc(notification.getMessageCategory().getOption())
                        .statusDesc(notification.getStatus().getOption())
                        .message(notification.getMessage())
                        .user(UserDTO.builder()
                                .id(notification.getUser().getId())
                                .name(notification.getUser().getName())
                                .email(notification.getUser().getEmail())
                                .phone(notification.getUser().getPhone())
                                .build())
                        .build())
                .toList();
    }
}
