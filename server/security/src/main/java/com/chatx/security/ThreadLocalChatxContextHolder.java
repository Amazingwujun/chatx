package com.chatx.security;

import com.chatx.commons.entity.ChatxContext;
import org.springframework.util.Assert;

/**
 * 用于保存请求上下文
 *
 * @author Jun
 * @see org.springframework.security.core.context.ThreadLocalSecurityContextHolderStrategy
 * @since 1.0.0
 */
@SuppressWarnings("JavadocReference")
public class ThreadLocalChatxContextHolder {

    private static final ThreadLocal<ChatxContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static ChatxContext getContext() {
        return contextHolder.get();
    }

    public static void setContext(ChatxContext context) {
        Assert.notNull(context, "Only non-null ChatxContext instances are permitted");
        contextHolder.set(context);
    }
}
