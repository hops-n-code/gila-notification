package com.hopsncode.challenge.notifications.api.catalog.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationTypeCatalog extends AbstractCatalog {
    public static final Long SMS = 200001L;
    public static final Long EMAIL = 200002L;
    public static final Long PUSH_NOTIFICATION = 200003L;

    @Override
    protected String getCatalogName() {
        return "NOTIFICATION_TYPE";
    }
}
