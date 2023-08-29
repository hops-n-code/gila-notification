package com.hopsncode.challenge.notifications.api.catalog.service;

import com.hopsncode.challenge.notifications.api.catalog.dto.CatalogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractCatalog {
    protected CatalogService catalogService;

    public List<CatalogDTO> getCatalogOptions() {
        return catalogService.findByCatalogName(getCatalogName());
    }

    public CatalogDTO findCatalogById(Long catalogId) {
        return catalogService.findCatalogById(catalogId, getCatalogName());
    }

    protected abstract String getCatalogName();

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }
}
