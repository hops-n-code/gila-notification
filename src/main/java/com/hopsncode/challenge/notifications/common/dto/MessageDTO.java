package com.hopsncode.challenge.notifications.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MessageDTO {
    private String identifier;
    private String message;
}
