package com.hopsncode.challenge.notifications.api.user.repository;

import com.hopsncode.challenge.notifications.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u" +
            " from User u" +
            " join u.messageCategories mc" +
            " where mc.id = :messageCategoryId")
    List<User> findByMessageCategory(Long messageCategoryId);
}