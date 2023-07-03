package com.chatx.broker.codec;

import com.chatx.broker.constants.ChatxMessageType;
import com.chatx.broker.constants.ChatxQos;
import com.chatx.broker.entity.ChatxFixedHeader;
import com.chatx.broker.entity.ChatxMessage;
import com.chatx.broker.entity.ChatxVariableHeader;
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
        READ_VARIABLE_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE
    }

    private DecoderState state = DecoderState.READ_FIXED_HEADER;
    private ChatxFixedHeader fixedHeader;
    private ChatxVariableHeader variableHeader;
    private int bytesRemainingInVariablePart;

    private final int maxBytesInMessage;

    public ChatxDecoder() {
        this.maxBytesInMessage = 1024 * 64;
    }

    public ChatxDecoder(int maxBytesInMessage) {
        this.maxBytesInMessage = maxBytesInMessage;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch (state) {
            case READ_FIXED_HEADER -> {
                try {
                    // 固定头读取
                    fixedHeader = decodeFixHeader(byteBuf);
                    if (fixedHeader == null) {
                        return;
                    }
                    bytesRemainingInVariablePart = fixedHeader.remainingLength();
                    if (bytesRemainingInVariablePart > maxBytesInMessage) {
                        byteBuf.skipBytes(actualReadableBytes());
                        throw new TooLongFrameException("too large message: " + bytesRemainingInVariablePart + " bytes");
                    }
                    state = DecoderState.READ_VARIABLE_HEADER;
                } catch (Exception e) {
                    out.add(invalidMessage(e));
                }
            }
            case READ_VARIABLE_HEADER -> {
                try {
                    if (byteBuf.readableBytes() < bytesRemainingInVariablePart) {
                        return;
                    }
                    var result = decodeVariableHeader(byteBuf);
                    variableHeader = result.value;
                    bytesRemainingInVariablePart -= result.numberOfBytesConsumed;
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
                    var result = decodePayload(byteBuf);
                    bytesRemainingInVariablePart -= result.numberOfBytesConsumed;
                    if (bytesRemainingInVariablePart != 0) {
                        throw new DecoderException("non-zero remaining payload bytes: " +
                                bytesRemainingInVariablePart + " (" + fixedHeader.messageType() + ')');
                    }
                    state = DecoderState.READ_FIXED_HEADER;
                    var msg = new ChatxMessage(fixedHeader, result.value, DecoderResult.SUCCESS);
                    fixedHeader = null;
                    variableHeader = null;
                    out.add(msg);
                } catch (Exception e) {
                    out.add(invalidMessage(e));
                }
            }
            case BAD_MESSAGE -> byteBuf.skipBytes(actualReadableBytes());
        }
    }

    private ChatxFixedHeader decodeFixHeader(ByteBuf buf) {
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

    private Result<ChatxVariableHeader> decodeVariableHeader(ByteBuf buf) {
        switch (fixedHeader.messageType()) {
            case CONNECT, CONNACK, PING, PONG, DISCONNECT -> {
                return new Result<>(ChatxVariableHeader.EMPTY_HEADER, 0);
            }
            case PUBLISH -> {
                if (fixedHeader.qosLevel() == ChatxQos.AT_MOST_ONCE) {
                    // 读取 cmdId
                    return new Result<>(new ChatxVariableHeader(buf.readInt(), Long.MIN_VALUE), 4);
                } else {
                    // 读取 cmdId, seqId
                    return new Result<>(new ChatxVariableHeader(buf.readInt(), buf.readLong()), 12);
                }
            }
            case PUBACK, PUBREC, PUBREL, PUBCOMP -> {
                // 读取 seqId
                return new Result<>(new ChatxVariableHeader(Integer.MIN_VALUE, buf.readLong()), 8);
            }
            default -> throw new IllegalArgumentException("非法的消息类型: " + fixedHeader.messageType());
        }
    }

    private ChatxMessage invalidMessage(Throwable cause) {
        state = DecoderState.BAD_MESSAGE;
        return new ChatxMessage(fixedHeader, null, DecoderResult.failure(cause));
    }

    private Result<byte[]> decodePayload(ByteBuf buf) {
        switch (fixedHeader.messageType()) {
            case CONNECT, CONNACK, PUBLISH, PUBACK, DISCONNECT -> {
                var data = new byte[bytesRemainingInVariablePart];
                buf.readBytes(data);
                return new Result<>(data, bytesRemainingInVariablePart);
            }
            default -> {
                return new Result<>(null, 0);
            }
        }
    }

    private record Result<T>(T value, int numberOfBytesConsumed) {
    }
}
