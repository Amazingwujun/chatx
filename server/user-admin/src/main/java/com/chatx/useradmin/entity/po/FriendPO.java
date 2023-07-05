package com.chatx.useradmin.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * t_friends 实体
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class FriendPO {

    private Integer id;

    private Integer userId;

    private Integer friendId;

    private LocalDateTime createAt;

    public FriendPO exchange(){
        var temp = userId;
        this.userId = friendId;
        this.friendId = temp;
        return this;
    }
}
