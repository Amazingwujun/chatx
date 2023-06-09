package com.chatx.commons.exception;

/**
 * 项目顶级异常
 *
 * @author Jun
 * @since 1.0.0
 */
public class ChatxException extends RuntimeException {

    public ChatxException() {
        super();
    }

    public ChatxException(String message) {
        super(message);
    }

    public ChatxException(String format, Object... args) {
        super(String.format(format, args));
    }

    public ChatxException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatxException(Throwable cause) {
        super(cause);
    }
}
