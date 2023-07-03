package com.chatx.broker.entity;

/**
 * 可变头
 *
 * @param cmdId 指令 id
 * @param seqId 报文 id
 * @author Jun
 * @since 1.0.0
 */
public record ChatxVariableHeader(int cmdId, long seqId) {

    public static final ChatxVariableHeader EMPTY_HEADER = new ChatxVariableHeader(Integer.MIN_VALUE, Long.MIN_VALUE);
}
