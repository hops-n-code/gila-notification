package com.hopsncode.challenge.notifications.api.catalog.service;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import com.hopsncode.challenge.notifications.api.catalog.repository.CatalogRepository;
import com.hopsncode.challenge.notifications.api.catalog.dto.CatalogDTO;
import com.hopsncode.challenge.notifications.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;


    public List<CatalogDTO> findByCatalogName(String catalogName) {
        return catalogRepository.findByNameOrderByIdAsc(catalogName).stream()
                .map(this::modelToDTO)
                .collect(Collectors.toList());
    }

    public CatalogDTO findCatalogById(Long catalogId, String catalogName) {
        return catalogRepository.findByIdAndName(catalogId, catalogName)
                .map(this::modelToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Catalog", "id", catalogId));
    }

    private CatalogDTO modelToDTO(Catalog model) {
        return CatalogDTO.builder()
                .id(model.getId())
                .option(model.getOption())
                .build();
    }
}
