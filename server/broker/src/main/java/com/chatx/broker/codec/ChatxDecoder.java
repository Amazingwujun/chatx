package com.chatx.broker.codec;

import com.chatx.broker.constants.ChatxMessageType;
import com.chatx.broker.constants.ChatxQos;
import com.chatx.broker.entity.ChatxFixedHeader;
import com.chatx.broker.entity.ChatxMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;


/**
 * chatx 协议解码器
 *
 * @author Jun
 * @since 1.0.0
 */
public class ChatxDecoder extends ByteToMessageDecoder {

    private enum DecoderState {
        READ_FIXED_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE
    }

    private DecoderState state = DecoderState.READ_FIXED_HEADER;
    private ChatxFixedHeader fixHeader;
    private int bytesRemainingInVariablePart;

    private final int maxBytesInMessage;

    public ChatxDecoder(int maxBytesInMessage) {
        this.maxBytesInMessage = maxBytesInMessage;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch (state) {
            case READ_FIXED_HEADER -> {
                try {
                    // 固定头读取
                    fixHeader = decodeFixHeader(ctx, byteBuf);
                    if (fixHeader == null) {
                        return;
                    }
                    bytesRemainingInVariablePart = fixHeader.remainingLength();
                    if (bytesRemainingInVariablePart > maxBytesInMessage) {
                        byteBuf.skipBytes(actualReadableBytes());
                        throw new TooLongFrameException("too large message: " + bytesRemainingInVariablePart + " bytes");
                    }
                    state = DecoderState.READ_PAYLOAD;
                } catch (Exception e) {
                    out.add(invalidMessage(e));
                }
            }
            case READ_PAYLOAD -> {
                try {
                    if (byteBuf.readableBytes() < bytesRemainingInVariablePart) {
                        return;
                    }


                    state = DecoderState.READ_FIXED_HEADER;
                    fixHeader = null;

                } catch (Exception e) {
                    out.add(invalidMessage(e));
                }
            }
            case BAD_MESSAGE -> byteBuf.skipBytes(actualReadableBytes());
        }
    }

    private ChatxFixedHeader decodeFixHeader(ChannelHandlerContext ctx, ByteBuf buf) {
        if (buf.readableBytes() < 2) {
            return null;
        }
        int readerIndex = buf.readerIndex();

        var b1 = buf.readUnsignedByte();
        var messageType = ChatxMessageType.valueOf(b1 >> 4);
        int qosLevel = (b1 & 0x06) >> 1;

        // todo 非法状态值检查

        int remainingLength = 0;
        int multiplier = 1;
        short digit;
        int loops = 0;
        do {
            if (buf.readUnsignedByte() < 1) {
                buf.readerIndex(readerIndex);
                return null;
            }
            digit = buf.readUnsignedByte();
            remainingLength += (digit & 127) * multiplier;
            multiplier *= 128;
            loops++;
        }
        while ((digit & 128) != 0 && loops < 4);

        if (loops == 4 && (digit & 128) != 0) {
            throw new DecoderException("remaining length exceeds 4 digits (" + messageType + ')');
        }

        return new ChatxFixedHeader(messageType, ChatxQos.valueOf(qosLevel), remainingLength);
    }

    private ChatxMessage invalidMessage(Throwable cause) {
        state = DecoderState.BAD_MESSAGE;
        return new ChatxMessage(fixHeader, null, DecoderResult.failure(cause));
    }

    private Result<?> decodePayload(ChannelHandlerContext ctx, ByteBuf buf) {

        return null;
    }

    private static final class Result<T> {

        public final T value;
        public final int numberOfBytesConsumed;

        Result(T value, int numberOfBytesConsumed) {
            this.value = value;
            this.numberOfBytesConsumed = numberOfBytesConsumed;
        }
    }
}
