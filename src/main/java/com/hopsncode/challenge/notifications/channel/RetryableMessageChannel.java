package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.common.dto.MessageDTO;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

public abstract class RetryableMessageChannel implements MessageChannel {

    @Override
    public Boolean sendMessage(MessageDTO messageDTO) {
        return Retry.decorateSupplier(
                        RetryRegistry.of(getRetryConfig()).retry("sendMessage"),
                        () -> _sendMessage(messageDTO))
                .get();
    }

    protected abstract Boolean _sendMessage(MessageDTO messageDTO);

    protected abstract RetryConfig getRetryConfig();
}
