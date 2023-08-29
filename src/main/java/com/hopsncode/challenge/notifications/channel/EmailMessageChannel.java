package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties("challenge.notifications.channels.email")
@Slf4j
public class EmailMessageChannel extends RetryableMessageChannel {
    private int maxAttempts = 3;

    @Override
    public Boolean _sendMessage(MessageDTO messageDTO) {
        log.info("Sending Email message: {}", messageDTO);
        return true;
    }

    @Override
    protected RetryConfig getRetryConfig() {
        return RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .build();
    }
}
