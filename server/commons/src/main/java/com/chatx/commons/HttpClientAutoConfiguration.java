package com.chatx.commons;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

/**
 * 配置 HttpClient
 *
 * @author Jun
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty("chatx.http-client.enabled")
public class HttpClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpClient httpClient(ChatxProperties chatxProperties) {
        return HttpClient.newBuilder()
                .connectTimeout(chatxProperties.getHttpClient().getConnectTimeout())
                .build();
    }
}
