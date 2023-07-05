package com.chatx.useradmin.service;

import com.chatx.commons.entity.ChatxResult;
import com.chatx.useradmin.entity.params.UserParams;
import com.chatx.useradmin.entity.po.UserPO;
import com.chatx.useradmin.entity.vo.UserVO;

/**
 * 用户服务接口
 *
 * @author Jun
 * @since 1.0.0
 */
public interface IUserService {



    /**
     * 用户注册
     *
     * @param params 入参
     */
    ChatxResult<Integer> signUp(UserParams params);

    /**
     * 用户登入
     *
     * @param params 入参
     */
    ChatxResult<String> signIn(UserParams params);

    /**
     * 通过 userId 找到指定用户
     *
     * @param userId 用户 id
     */
    UserPO find(Integer userId);
}
