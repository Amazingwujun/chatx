package com.chatx.security;

import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用于配置 {@link com.chatx.commons.entity.ChatxContext} 拦截器.
 *
 * @author Jun
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class ChatxContextAwareAutoConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(JWTVerifier jwtVerifier){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry
                        .addInterceptor(new ChatxContextInterceptor(jwtVerifier))
                        .addPathPatterns("/**")
                        .excludePathPatterns(
                                "/user/sign-in",
                                "/user/sign-up"
                        );
            }
        };
    }
}
