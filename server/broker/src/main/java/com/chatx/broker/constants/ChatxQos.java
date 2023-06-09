package com.chatx.broker.constants;

/**
 * chatx qos
 *
 * @author Jun
 * @since 1.0.0
 */
public enum ChatxQos {
    AT_MOST_ONCE(0),
    AT_LEAST_ONCE(1),
    EXACTLY_ONCE(2),
    FAILURE(0x80);

    private final int value;

    ChatxQos(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ChatxQos valueOf(int value) {
        return switch (value) {
            case 0 -> AT_MOST_ONCE;
            case 1 -> AT_LEAST_ONCE;
            case 2 -> EXACTLY_ONCE;
            case 0x80 -> FAILURE;
            default -> throw new IllegalArgumentException("invalid QoS: " + value);
        };
    }
}
