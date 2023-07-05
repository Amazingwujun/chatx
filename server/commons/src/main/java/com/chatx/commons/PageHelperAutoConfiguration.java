package com.chatx.commons;

import com.github.pagehelper.PageInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * page helper 配置
 *
 * @author Jun
 * @since 1.0.0
 */
@ConditionalOnClass(PageInterceptor.class)
@Configuration
public class PageHelperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PageInterceptor pageInterceptor() {
        var pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new Properties());
        return pageInterceptor;
    }
}
