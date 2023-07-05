package com.chatx.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.chatx.commons.constants.CommonResponseCode;
import com.chatx.commons.entity.ChatxContext;
import com.chatx.commons.entity.ChatxResult;
import com.chatx.commons.exception.ChatxException;
import com.chatx.commons.utils.JSON;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从请求体中抓获取 {@link com.chatx.commons.entity.ChatxContext}
 *
 * @author Jun
 * @since 1.0.0
 */
public class ChatxContextInterceptor implements HandlerInterceptor {

    private static final String TOKEN_KEY = "x-token";
    private final JWTVerifier jwtVerifier;

    public ChatxContextInterceptor(JWTVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        // 1. 令牌获取
        String token = req.getHeader(TOKEN_KEY);
        if (token == null) {
            return responseOnFail(resp, ChatxResult.error(CommonResponseCode.UNAUTHORIZED));
        }

        // 2. 令牌校验
        DecodedJWT verify;
        try {
            verify = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            if (e instanceof TokenExpiredException) {
                return responseOnFail(resp, ChatxResult.fail(CommonResponseCode.TOKEN_EXPIRE));
            }

            return responseOnFail(resp, ChatxResult.fail(CommonResponseCode.TOKEN_ERR));
        }

        // 3. context 构建
        final var userId = verify.getClaim("userId").asInt();
        final var nickname = verify.getClaim("nickname").asString();
        ThreadLocalChatxContextHolder.setContext(new ChatxContext(userId, nickname));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadLocalChatxContextHolder.clearContext();
    }

    private boolean responseOnFail(HttpServletResponse resp, ChatxResult<?> result) {
        try {
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            var os = resp.getOutputStream();
            os.write(JSON.writeValueAsBytes(result));
            return false;
        } catch (IOException e) {
            throw new ChatxException("响应写入失败: %s", e.getMessage());
        }
    }
}
