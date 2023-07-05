package com.chatx.useradmin.controller;

import com.chatx.commons.entity.ChatxResult;
import com.chatx.security.BaseController;
import com.chatx.useradmin.entity.params.FriendParams;
import com.chatx.useradmin.service.IFriendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 朋友 API
 *
 * @author Jun
 * @since 1.0.0
 */
@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    private final IFriendService friendService;

    public FriendController(IFriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/invite")
    public ChatxResult<Void> invite(@RequestBody FriendParams params){
        return friendService.invite(context(), params);
    }

    @PostMapping("/agree")
    public ChatxResult<Void> agree(@RequestBody FriendParams params) {
        return friendService.agree(context(), params);
    }

    @PostMapping("/break-up")
    public ChatxResult<Void> breakUp(@RequestBody FriendParams params){
        return friendService.breakUp(context(), params);
    }
}
