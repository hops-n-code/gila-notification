package com.hopsncode.challenge.notifications.channel;

import com.hopsncode.challenge.notifications.common.dto.MessageDTO;

public interface MessageChannel {
    Boolean sendMessage(MessageDTO messageDTO);
}
