package com.hopsncode.challenge.notifications.api.catalog.service;

import com.hopsncode.challenge.notifications.api.catalog.dto.CatalogDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Tag("Integration")
class NotificationStatusCatalogTest {
    @Autowired
    private NotificationStatusCatalog notificationStatusCatalog;

    @Test
    void getCatalogOptionsReturnsExpected() {
        List<CatalogDTO> catalogOptions = notificationStatusCatalog.getCatalogOptions();
        assertThat(catalogOptions)
                .hasSize(3)
                .extracting(CatalogDTO::getId, CatalogDTO::getOption)
                .containsAll(List.of(
                        tuple(NotificationStatusCatalog.PENDING, "Pending"),
                        tuple(NotificationStatusCatalog.SENT, "Sent"),
                        tuple(NotificationStatusCatalog.FAILED, "Failed")));
    }
}