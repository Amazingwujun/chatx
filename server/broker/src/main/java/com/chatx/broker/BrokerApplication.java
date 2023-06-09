package com.chatx.broker;

import com.chatx.broker.config.BizProperties;
import com.chatx.commons.ChatxApp;
import com.chatx.commons.ChatxProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ChatxProperties.class, BizProperties.class})
@SpringBootApplication(scanBasePackages = "com.chatx")
public class BrokerApplication {

    public static void main(String[] args) {
        ChatxApp.run(BrokerApplication.class, args);
    }

}
