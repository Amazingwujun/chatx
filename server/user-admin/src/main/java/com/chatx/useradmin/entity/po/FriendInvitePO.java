package com.chatx.useradmin.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 好友申请 PO
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class FriendInvitePO {

    private Integer inviterId;

    private Integer inviteeId;

    private String remark;

    private LocalDateTime inviteAt;
}
