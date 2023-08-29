package com.hopsncode.challenge.notifications.api.notification.dto;

import com.hopsncode.challenge.notifications.api.user.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Jacksonized
@Getter
public class SentNotificationDTO {
    private Long id;
    private LocalDateTime createdDateTime;
    private String message;
    private String statusDesc;
    private Long typeId;
    private String typeDesc;
    private String categoryDesc;
    private UserDTO user;
}
