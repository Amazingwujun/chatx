package com.chatx.useradmin.dao;

import com.chatx.useradmin.entity.po.FriendInvitePO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * 朋友 dao
 *
 * @author Jun
 * @since 1.0.0
 */
@Mapper
public interface FriendMapper {

    @Insert("""
            replace into t_friend_request
            (inviter_id, invitee_id, remark, invite_at)
            values 
            (#{inviterId}, #{inviteeId}, #{remark}, #{inviteAt})
            """)
    int invite(FriendInvitePO po);

    @Select("""
            select 
            count(*)
            from t_friends
            where (user_id = #{userId} and friend_id = #{friendId})
            or (user_id = #{friendId} and friend_id = #{userId})
            for share
            """)
    int isFriend(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

    @Select("""
            select 
            inviter_id, invitee_id, remark, invite_at
            from t_friend_request
            where inviter_id = #{inviter_id}
            and #{invitee_id}
            """)
    FriendInvitePO selectInvite(@Param("inviterId") Integer inviterId, @Param("inviteeId") Integer inviteeId);

    @Insert("""
            <script>
            insert into t_friends(user_id, friend_id, create_at)
            VALUES
            <foreach collection="friends" item="item" open="(" close=")" separator=",">
                (#{item.userId}, #{item.friendId}, #{item.createAt})
            </foreach>
            </script>
            """)
    int make(ArrayList<Object> friends);

    @Delete("""
            delete
            from t_friend_request
            where (inviter_id = #{inviterId} and invitee_id = #{inviteeId})
            or (inviter_id = #{inviteeId} and invitee_id = #{inviterId})
            """)
    int clearInvite(Integer inviterId, Integer inviteeId);

    @Delete("""
            delete
            from t_friends
            where user_id = #{userId}
            and friend_id = #{friendId}
            """)
    int breakUp(Integer userId, Integer friendId);
}
