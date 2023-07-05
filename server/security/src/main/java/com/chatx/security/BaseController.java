package com.chatx.security;

import com.chatx.commons.entity.ChatxContext;
import com.chatx.commons.exception.ChatxException;

/**
 * 基础控制器, 为其它 controller 提供通用方法
 *
 * @author Jun
 * @since 1.0.0
 */
public abstract class BaseController {

    /**
     * 通过 {@link ThreadLocalChatxContextHolder} 获取请求内容中的部分字段作为上下文
     *
     * @return {@link com.chatx.commons.entity.ChatxContext}
     */
    protected ChatxContext context() {
        var ctx = ThreadLocalChatxContextHolder.getContext();
        if (ctx == null) {
            throw new ChatxException("ChatxException 获取失败");
        }

        return ctx;
    }
}
