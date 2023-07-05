package com.chatx.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.chatx.commons.ChatxApp;
import com.chatx.commons.exception.SslException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * JWT 相关配置
 *
 * @author Jun
 * @since 1.0.0
 */
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnClass({Algorithm.class, JWTVerifier.class})
public class JwtAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Algorithm algorithm(JwtProperties properties, ResourceLoader resourceLoader) {
        // 获取 store 密码， keypass 与 storepass 密码相同
        String storePassword = properties.getKeyStorePassword();
        final String keyAlias = properties.getKeyAlias();

        RSAPublicKey publicKey;
        RSAPrivateKey privateKey;
        try {
            Resource pk = resourceLoader.getResource(properties.getKeyStoreLocation());
            final KeyStore keyStore = KeyStore.getInstance("pkcs12");
            keyStore.load(pk.getInputStream(), storePassword.toCharArray());
            publicKey = (RSAPublicKey) keyStore.getCertificate(keyAlias).getPublicKey();
            privateKey = (RSAPrivateKey) keyStore.getKey(keyAlias, storePassword.toCharArray());
            return Algorithm.RSA384(publicKey, privateKey);
        } catch (Exception e) {
            throw new SslException(e);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public JWTVerifier jwtVerifier(Algorithm algorithm) {
        return JWT.require(algorithm)
                .withIssuer(ChatxApp.PROJECT_NAME)
                .build();
    }
}
