package com.chatx.commons.entity;

/**
 * 请求上下文
 *
 * @param userId   用户id
 * @param nickname 用户昵称
 * @author Jun
 * @since 1.0.0
 */
public record ChatxContext(Integer userId, String nickname) {

    public static final String HEADER_KEY = "chatx-context";
}
