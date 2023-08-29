package com.hopsncode.challenge.notifications.api.catalog.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "catalog",
        uniqueConstraints = @UniqueConstraint(name = "uq_catalog_option", columnNames = {"name", "option"}))
public class Catalog {
    @Id
    private Long id;
    private String name;
    private String option;

    public static Catalog fromId(Long id) {
        return builder().id(id).build();
    }
}