package com.hopsncode.challenge.notifications.api.notification.dto;

import com.hopsncode.challenge.notifications.common.validation.group.OnSendNotification;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
@ToString
public class MessageRequestDTO {
    @NotNull(groups = OnSendNotification.class)
    private Long categoryId;

    @NotEmpty(groups = OnSendNotification.class)
    @Size(max = 1000, groups = OnSendNotification.class)
    private String message;
}
