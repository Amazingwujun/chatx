package com.chatx.commons;

import com.chatx.commons.utils.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ObjectMapper} jackson mapper 自动化配置.
 *
 * @author Jun
 * @since 1.0.0
 */
@Configuration
public class ObjectMapperAutoConfiguration {

    @Bean
    @ConditionalOnClass(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return JSON.getObjectMapper();
    }
}
