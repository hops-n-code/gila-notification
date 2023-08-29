package com.hopsncode.challenge.notifications.api.catalog.service;

import org.springframework.stereotype.Service;

@Service
public class MessageCategoryCatalog extends AbstractCatalog {
    @Override
    protected String getCatalogName() {
        return "MESSAGE_CATEGORY";
    }
}
