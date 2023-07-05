package com.chatx.useradmin.service.impl;

import com.chatx.commons.entity.ChatxContext;
import com.chatx.commons.entity.ChatxResult;
import com.chatx.useradmin.constants.ResponseCode;
import com.chatx.useradmin.dao.FriendMapper;
import com.chatx.useradmin.entity.params.FriendParams;
import com.chatx.useradmin.entity.po.FriendPO;
import com.chatx.useradmin.service.IFriendService;
import com.chatx.useradmin.service.IUserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * 朋友相关接口实现
 *
 * @author Jun
 * @since 1.0.0
 */
@Service
public class FriendServiceImpl implements IFriendService {

    private final IUserService userService;
    private final FriendMapper friendMapper;

    public FriendServiceImpl(IUserService userService,
                             FriendMapper friendMapper) {
        this.userService = userService;
        this.friendMapper = friendMapper;
    }

    /*
            好友关系是双向的，a -> b, b -> a 是才表明双方是好友，否则只是单方面的好友关系。
     */

    @Override
    @Transactional
    public ChatxResult<Void> invite(ChatxContext context, FriendParams params) {
        var inviteeId = params.getUserId();

        // 检查用户是否存在
        var userPO = userService.find(inviteeId);
        if (userPO == null) {
            return ChatxResult.fail(ResponseCode.DATA_NOT_EXIST, "用户不存在");
        }

        // 检查是否已经是好友
        if (friendMapper.isFriend(context.userId(), inviteeId) > 0) {
            return ChatxResult.fail(ResponseCode.DATA_CREATE_ERR, "已是好友关系");
        }

        // 发起请求
        var friendInvitePO = params.toFriendInvitePO(context);
        friendMapper.invite(friendInvitePO);


        return ChatxResult.ok();
    }

    @Override
    public ChatxResult<Void> agree(ChatxContext context, FriendParams params) {
        final var inviterId = params.getUserId();
        final var inviteeId = context.userId();

        // 检查受邀记录
        if (friendMapper.selectInvite(inviterId, inviteeId) == null) {
            return ChatxResult.fail(ResponseCode.DATA_NOT_EXIST, "未发现好友申请记录");
        }

        // 插入 t_friends 表
        var friendPO = params.toFriendPO(context);
        var friends = new ArrayList<>(2);
        friends.add(friendPO);
        friends.add(friendPO.exchange());
        try {
            friendMapper.make(friends);
        } catch (DuplicateKeyException e) {
            return ChatxResult.fail(ResponseCode.DATA_ALREADY_EXIST, "好友关系已存在");
        }

        // 删除相关记录
        friendMapper.clearInvite(inviterId, inviteeId);

        return ChatxResult.ok();
    }

    @Override
    public ChatxResult<Void> breakUp(ChatxContext context, FriendParams params) {
        final var friendId = params.getUserId();

        // 解除好友关系
        friendMapper.breakUp(context.userId(), friendId);

        return ChatxResult.ok();
    }
}
