package com.chatx.useradmin.service;

import com.chatx.commons.entity.ChatxContext;
import com.chatx.commons.entity.ChatxResult;
import com.chatx.useradmin.entity.params.FriendParams;

/**
 * 朋友相关接口
 *
 * @author Jun
 * @since 1.0.0
 */
public interface IFriendService {

    /**
     * 申请好友
     *
     * @param context 请求上下文
     * @param params 入参
     */
    ChatxResult<Void> invite(ChatxContext context, FriendParams params);

    /**
     * 同意添加好友
     *
     * @param context 请求上下文
     * @param params 入参
     */
    ChatxResult<Void> agree(ChatxContext context, FriendParams params);

    /**
     * 与对方解除好友关系
     *
     * @param context 请求上下文
     * @param params 入参
     */
    ChatxResult<Void> breakUp(ChatxContext context, FriendParams params);
}
