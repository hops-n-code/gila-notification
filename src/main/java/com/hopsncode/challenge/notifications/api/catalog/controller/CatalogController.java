package com.hopsncode.challenge.notifications.api.catalog.controller;

import com.hopsncode.challenge.notifications.api.catalog.service.MessageCategoryCatalog;
import com.hopsncode.challenge.notifications.api.catalog.dto.CatalogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final MessageCategoryCatalog messageCategoryCatalog;

    @GetMapping("/message-category")
    public ResponseEntity<List<CatalogDTO>> getMessageCategoryCatalog() {
        return ResponseEntity.ok(messageCategoryCatalog.getCatalogOptions());
    }

}
