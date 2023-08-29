package com.hopsncode.challenge.notifications.api.notification.model;

import com.hopsncode.challenge.notifications.api.catalog.model.Catalog;
import com.hopsncode.challenge.notifications.api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id")
    private Catalog status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private Catalog type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "message_category_id")
    private Catalog messageCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    public void updateStatus(Long statusId) {
        this.status = Catalog.fromId(statusId);
    }
}