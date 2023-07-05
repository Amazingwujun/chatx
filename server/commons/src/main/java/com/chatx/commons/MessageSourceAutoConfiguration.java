package com.chatx.commons;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * <h1>MessageSource 自动配置.</h1>
 * <p>
 *  springboot 自带 {@link org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration}, 我们生成新的
 *  {@link MessageSource} Bean 是为了防止同样的 <code>messages.properties</code> 被覆盖. 逻辑见 {@link com.chatx.commons.utils.MessageUtils}
 * </p>
 *
 * @author Jun
 * @since 1.0.0
 */
@Configuration
public class MessageSourceAutoConfiguration {

    @Bean("commonMessageSource")
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("common_messages");
        messageSource.setDefaultEncoding("utf8");

        return messageSource;
    }
}
