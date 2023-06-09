package com.chatx.commons;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * chatx 项目配置
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "chatx")
public class ChatxProperties {

    //@formatter:off

    /** 项目名称，中文名。用于启动提示 */
    private String name;

    /** 项目描述 */
    private String desc;

    private HttpClientProperties httpClient = new HttpClientProperties();

    /**
     * {@link java.net.http.HttpClient} 配置
     */
    @Data
    public static class HttpClientProperties {
        //@formatter:off

        private boolean enabled;

        /** tcp 连接完成超时 */
        private Duration connectTimeout = Duration.ofSeconds(3);

        /** 读数据超时 */
        private Duration readTimeout = Duration.ofSeconds(3);

        //@formatter:on
    }
}
