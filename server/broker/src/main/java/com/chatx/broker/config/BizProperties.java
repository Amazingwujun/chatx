package com.chatx.broker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 业务配置
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "chatx.broker")
public class BizProperties {

    /** broker 端口号 */
    private int port = 1203;

    /** tcp 握手队列 */
    private int soBacklog = 128;
}
