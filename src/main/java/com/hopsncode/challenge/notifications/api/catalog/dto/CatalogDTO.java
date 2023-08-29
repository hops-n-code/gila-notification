package com.hopsncode.challenge.notifications.api.catalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class CatalogDTO {
    private Long id;
    private String option;
}
