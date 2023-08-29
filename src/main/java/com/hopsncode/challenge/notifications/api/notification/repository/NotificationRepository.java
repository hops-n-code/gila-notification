package com.hopsncode.challenge.notifications.api.notification.repository;

import com.hopsncode.challenge.notifications.api.notification.model.Notification;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}