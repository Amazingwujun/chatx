package com.chatx.broker.codec;

import com.chatx.broker.constants.ChatxQos;
import com.chatx.broker.entity.ChatxFixedHeader;
import com.chatx.broker.entity.ChatxMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Jun
 * @since 1.0.0
 */
public class ChatxEncoder extends MessageToByteEncoder<ChatxMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChatxMessage msg, ByteBuf out) throws Exception {
        var fixedHeader = msg.fixedHeader();
        switch (fixedHeader.messageType()) {
            case CONNECT -> {
                var len = msg.payload().length;
                out.writeByte(getFixedHeaderByte1(fixedHeader));
                writeVariableLengthInt(out, len);
                out.writeBytes(msg.payload());
            }
            case PUBLISH -> {
                if (fixedHeader.qosLevel() == ChatxQos.AT_MOST_ONCE) {
                    // 只有 cmdId
                    var len = 4;
                    len += msg.payload().length;
                } else {
                    // cmdId + seqId
                }
            }
            case PUBACK -> {

            }
            case PONG -> {
                out.writeByte(getFixedHeaderByte1(fixedHeader));
                out.writeByte(0);
            }
        }
    }

    private static void writeVariableLengthInt(ByteBuf buf, int len) {
        do {
            int digit = len % 128;
            len /= 128;
            if (len > 0) {
                digit |= 0x80;
            }
            buf.writeByte(digit);
        }
        while (len > 0);
    }

    private static int getFixedHeaderByte1(ChatxFixedHeader header) {
        int ret = 0;
        ret |= header.messageType().value() << 4;
        ret |= header.qosLevel().value() << 1;
        return ret;
    }
}
