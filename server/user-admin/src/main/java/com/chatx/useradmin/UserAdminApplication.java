package com.chatx.useradmin;

import com.chatx.commons.ChatxApp;
import com.chatx.commons.ChatxProperties;
import com.chatx.security.ChatxContextInterceptor;
import com.chatx.useradmin.config.BizProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ChatxProperties.class, BizProperties.class})
@SpringBootApplication(scanBasePackages = "com.chatx")
public class UserAdminApplication {

    public static void main(String[] args) {
        ChatxApp.run(UserAdminApplication.class, args);
    }

}
