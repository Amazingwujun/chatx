package com.chatx.useradmin.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chatx.commons.ChatxApp;
import com.chatx.commons.entity.ChatxResult;
import com.chatx.commons.utils.Uuids;
import com.chatx.useradmin.config.BizProperties;
import com.chatx.useradmin.constants.ResponseCode;
import com.chatx.useradmin.dao.UserMapper;
import com.chatx.useradmin.entity.params.UserParams;
import com.chatx.useradmin.entity.po.UserPO;
import com.chatx.useradmin.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

/**
 * 用户服务接口实现
 *
 * @author Jun
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final Algorithm algorithm;
    private final Duration tokenExpireInterval;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final String userTokenPrefix;

    public UserServiceImpl(Algorithm algorithm,
                           BizProperties bizProperties,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.algorithm = algorithm;
        this.tokenExpireInterval = bizProperties.getTokenExpireInterval();
        this.userTokenPrefix = bizProperties.getUserTokenPrefix();
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public ChatxResult<Integer> signUp(UserParams params) {
        var createPO = params.toPO();

        // 插入对象
        createPO.encodePassword(passwordEncoder);
        userMapper.insertOne(createPO);

        return ChatxResult.ok(createPO.getId());
    }

    @Override
    public ChatxResult<String> signIn(UserParams params) {
        final var uid = params.getId();
        final var passwd = params.getPasswd();

        // 查找用户
        var userPO = userMapper.selectById(uid);
        if (userPO == null) {
            return ChatxResult.fail(ResponseCode.DATA_NOT_EXIST, "用户不存在");
        }

        // 密码比对
        if (!passwordEncoder.matches(passwd, userPO.getPasswd())) {
            return ChatxResult.fail(ResponseCode.SIGN_IN_ERR);
        }

        // token 生成
        var tokenId = Uuids.random();
        var builder = JWT.create()
                .withIssuer(ChatxApp.PROJECT_NAME)
                .withClaim("userId", uid)
                .withClaim("tokenId", tokenId)
                .withClaim("nickname", userPO.getNickname())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpireInterval.toMillis()));
        String token = builder.sign(algorithm);

        return ChatxResult.ok(token);
    }

    @Override
    public UserPO find(Integer userId) {
        return userMapper.selectById(userId);
    }
}
