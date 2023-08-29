package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties("challenge.notifications.channels.sms")
@Slf4j
public class SmsMessageChannel extends RetryableMessageChannel {

    private int maxAttempts = 3;
    private boolean useExponentialBackOff = true;

    @Override
    protected Boolean _sendMessage(MessageDTO messageDTO) {
        log.info("Sending SMS message: {}", messageDTO);
        return true;
    }

    @Override
    protected RetryConfig getRetryConfig() {
        RetryConfig.Builder<Object> builder = RetryConfig.custom()
                .maxAttempts(maxAttempts);
        if (useExponentialBackOff) {
            builder.intervalFunction(IntervalFunction.ofExponentialBackoff());
        }

        return builder.build();
    }
}
