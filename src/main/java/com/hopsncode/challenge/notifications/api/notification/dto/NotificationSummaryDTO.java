package com.hopsncode.challenge.notifications.api.notification.dto;

import lombok.Getter;

@Getter
public class NotificationSummaryDTO {
    private int sent = 0;
    private int failed = 0;
    private int pending;
    private final int total;

    public NotificationSummaryDTO(int total) {
        this.total = total;
        this.pending = this.total;
    }

    public void recordSent() {
        if (pending == 0) return;

        sent++;
        pending--;
    }

    public void recordFailed() {
        if (pending == 0) return;

        failed++;
        pending--;
    }
}
