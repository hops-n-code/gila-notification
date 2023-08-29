package com.hopsncode.challenge.notifications.api.user.model;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    @ManyToMany
    @JoinTable(name = "user_message_category",
            joinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "fk_user_message_category_user"),
            inverseJoinColumns = @JoinColumn(name = "message_category_id"),
            inverseForeignKey = @ForeignKey(name = "fk_user_message_category_category")
    )
    @Builder.Default
    private Set<Catalog> messageCategories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_notification_type",
            joinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "fk_user_notification_type_user"),
            inverseJoinColumns = @JoinColumn(name = "notification_type_id"),
            inverseForeignKey = @ForeignKey(name = "fk_user_notification_type_notification_type")
    )
    @Builder.Default
    private Set<Catalog> notificationTypes = new HashSet<>();

}
