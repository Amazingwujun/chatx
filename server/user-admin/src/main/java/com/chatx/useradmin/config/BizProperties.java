package com.chatx.useradmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 业务配置
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "chatx.user-admin")
public class BizProperties {
    //@formatter:off

    /** 令牌过期时间 */
    private Duration tokenExpireInterval = Duration.ofDays(3);

    /** 用户令牌前缀 */
    private String userTokenPrefix = "user-admin:user:token:";
    //@formatter:off
}
