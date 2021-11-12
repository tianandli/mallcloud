package com.lijie.oauth2serverjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 功能描述：使用Jwt存储token
 *
 * @author: lijie
 * @date: 2021/11/12 14:25
 * @version: V1.0
 */
@Configuration
public class JwtTokenStoreConfig {
    /**
     * 描述：Jwt内容增强需要加这一个
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("wwwww");//配置JWT使用的秘钥
        return accessTokenConverter;
    }
}
