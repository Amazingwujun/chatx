package com.chatx.useradmin.entity.params;

import com.chatx.commons.entity.ChatxContext;
import com.chatx.useradmin.entity.po.FriendInvitePO;
import com.chatx.useradmin.entity.po.FriendPO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Friend 接口入参
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class FriendParams {

    private Integer userId;

    @Length(max = 200, message = "申请理由过长")
    private String inviteRemark;

    public FriendInvitePO toFriendInvitePO(ChatxContext context){
        return new FriendInvitePO()
                .setInviterId(context.userId())
                .setInviteeId(userId)
                .setRemark(inviteRemark)
                .setInviteAt(LocalDateTime.now());
    }

    public FriendPO toFriendPO(ChatxContext context){
        return new FriendPO()
                .setUserId(context.userId())
                .setFriendId(userId)
                .setCreateAt(LocalDateTime.now());
    }
}
