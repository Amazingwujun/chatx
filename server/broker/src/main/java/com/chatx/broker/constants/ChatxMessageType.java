package com.chatx.broker.constants;

/**
 * chatx 消息类型
 *
 * @author Jun
 * @since 1.0.0
 */
public enum ChatxMessageType {
    CONNECT(1),
    CONNACK(2),
    PUBLISH(3),
    PUBACK(4),
    PUBREC(5),
    PUBREL(6),
    PUBCOMP(7),
    PING(8),
    PONG(9),
    DISCONNECT(10);

    private static final ChatxMessageType[] VALUES;

    static {
        // this prevent values to be assigned with the wrong order
        // and ensure valueOf to work fine
        final ChatxMessageType[] values = values();
        VALUES = new ChatxMessageType[values.length + 1];
        for (var chatxMessageType : values) {
            final int value = chatxMessageType.value;
            if (VALUES[value] != null) {
                throw new AssertionError("value already in use: " + value);
            }
            VALUES[value] = chatxMessageType;
        }
    }

    private final int value;

    ChatxMessageType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ChatxMessageType valueOf(int type) {
        if (type <= 0 || type >= VALUES.length) {
            throw new IllegalArgumentException("unknown message type: " + type);
        }
        return VALUES[type];
    }
}
