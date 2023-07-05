package com.chatx.useradmin.controller;

import com.chatx.commons.entity.ChatxContext;
import com.chatx.commons.entity.ChatxPageInfo;
import com.chatx.commons.entity.ChatxResult;
import com.chatx.commons.validate.Create;
import com.chatx.security.BaseController;
import com.chatx.useradmin.entity.params.UserParams;
import com.chatx.useradmin.entity.vo.UserVO;
import com.chatx.useradmin.service.IUserService;
import com.chatx.useradmin.validate.SignIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 API
 *
 * @author Jun
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ChatxResult<Integer> signUp(@Validated(Create.class) @RequestBody UserParams params){
        return userService.signUp(params);
    }

    @PostMapping("/sign-in")
    public ChatxResult<String> signIn(@Validated(SignIn.class) @RequestBody UserParams params){
        return userService.signIn(params);
    }
}
