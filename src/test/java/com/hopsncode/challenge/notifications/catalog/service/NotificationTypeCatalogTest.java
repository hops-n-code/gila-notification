package com.hopsncode.challenge.notifications.catalog.service;

import com.hopsncode.challenge.notifications.api.catalog.dto.CatalogDTO;
import com.hopsncode.challenge.notifications.api.catalog.service.NotificationTypeCatalog;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Tag("Integration")
class NotificationTypeCatalogTest {
    @Autowired
    private NotificationTypeCatalog notificationTypeCatalog;

    @Test
    void getCatalogOptionsReturnsExpected() {
        List<CatalogDTO> catalogOptions = notificationTypeCatalog.getCatalogOptions();
        assertThat(catalogOptions)
                .hasSize(3)
                .extracting(CatalogDTO::getId, CatalogDTO::getOption)
                .containsAll(List.of(
                        tuple(NotificationTypeCatalog.SMS, "SMS"),
                        tuple(NotificationTypeCatalog.EMAIL, "E-mail"),
                        tuple(NotificationTypeCatalog.PUSH_NOTIFICATION, "Push Notification")));
    }

    @Test
    void findCatalogByIdReturnsExpected() {
        assertThat(notificationTypeCatalog.findCatalogById(NotificationTypeCatalog.SMS))
                .extracting(CatalogDTO::getId, CatalogDTO::getOption)
                .containsAll(List.of(NotificationTypeCatalog.SMS, "SMS"));
    }
}