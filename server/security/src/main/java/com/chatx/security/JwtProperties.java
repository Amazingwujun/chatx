package com.chatx.security;

import com.chatx.commons.ChatxApp;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置项
 *
 * @author Jun
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "chatx.jwt")
public class JwtProperties {

    /** keyStore 密码 */
    private String keyStorePassword = "123456";

    /** keyStore 位置 */
    private String keyStoreLocation = "classpath:keystore/chatx.ks";

    /** key 别名 */
    private String keyAlias = ChatxApp.PROJECT_NAME;

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreLocation() {
        return keyStoreLocation;
    }

    public void setKeyStoreLocation(String keyStoreLocation) {
        this.keyStoreLocation = keyStoreLocation;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }
}
