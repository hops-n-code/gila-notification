package com.hopsncode.challenge.notifications.api.catalog.repository;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    List<Catalog> findByNameOrderByIdAsc(String catalogName);

    Optional<Catalog> findByIdAndName(Long catalogId, String catalogName);
}