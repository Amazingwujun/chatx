package com.chatx.broker.entity;

import com.chatx.broker.constants.ChatxMessageType;
import com.chatx.broker.constants.ChatxQos;

/**
 * chatx 报文固定头
 *
 * @author Jun
 * @since 1.0.0
 */
public record ChatxFixedHeader(ChatxMessageType messageType, ChatxQos qosLevel, int remainingLength) {
}
