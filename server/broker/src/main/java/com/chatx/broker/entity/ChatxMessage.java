package com.chatx.broker.entity;

import com.chatx.broker.constants.ChatxMessageType;
import com.chatx.broker.constants.ChatxQos;
import io.netty.handler.codec.DecoderResult;

/**
 * chatx 报文
 *
 * @author Jun
 * @since 1.0.0
 */
public class ChatxMessage {

    private final ChatxFixedHeader chatxFixedHeader;
    private final Object payload;
    private final DecoderResult decoderResult;

    public static final ChatxMessage PING = new ChatxMessage(new ChatxFixedHeader(ChatxMessageType.PING, ChatxQos.AT_MOST_ONCE, 0), null);
    public static final ChatxMessage PONG = new ChatxMessage(new ChatxFixedHeader(ChatxMessageType.PONG, ChatxQos.AT_MOST_ONCE, 0), null);

    public ChatxMessage(ChatxFixedHeader chatxFixedHeader) {
        this(chatxFixedHeader, null);
    }

    public ChatxMessage(ChatxFixedHeader chatxFixedHeader, Object payload) {
        this(chatxFixedHeader, payload, DecoderResult.SUCCESS);
    }

    public ChatxMessage(ChatxFixedHeader chatxFixedHeader, Object payload, DecoderResult decoderResult) {
        this.chatxFixedHeader = chatxFixedHeader;
        this.payload = payload;
        this.decoderResult = decoderResult;
    }

    public ChatxFixedHeader fixedHeader(){
        return chatxFixedHeader;
    }

    public Object payload() {
        return payload;
    }

    public DecoderResult decoderResult(){
        return decoderResult;
    }

    @Override
    public String toString() {
        return "ChatxMessage{" +
                "chatxFixedHeader=" + chatxFixedHeader +
                ", payload=" + payload +
                ", decoderResult=" + decoderResult +
                '}';
    }
}
