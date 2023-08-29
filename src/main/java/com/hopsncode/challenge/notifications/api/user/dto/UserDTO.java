package com.hopsncode.challenge.notifications.api.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phone;
}
