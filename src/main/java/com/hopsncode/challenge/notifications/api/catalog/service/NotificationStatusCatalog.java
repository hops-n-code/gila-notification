package com.hopsncode.challenge.notifications.api.catalog.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationStatusCatalog extends AbstractCatalog {
    public static final Long PENDING = 300001L;
    public static final Long SENT = 300002L;
    public static final Long FAILED = 300003L;

    @Override
    protected String getCatalogName() {
        return "NOTIFICATION_STATUS";
    }
}
