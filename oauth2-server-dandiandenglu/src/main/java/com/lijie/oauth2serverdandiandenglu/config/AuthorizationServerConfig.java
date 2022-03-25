package com.lijie.oauth2serverdandiandenglu.config;

import com.lijie.oauth2serverdandiandenglu.component.JwtTokenEnhancer;
import com.lijie.oauth2serverdandiandenglu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：配置认证服务器
 *
 * @author: lijie
 * @date: 2021/11/12 9:25
 * @version: V1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

//    @Autowired
//    @Qualifier("redisTokenStore")
//    @Qualifier("jwtTokenStore")
//    private TokenStore tokenStore;
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

//    /**
//     * 使用密码模式需要配置
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> delegates = new ArrayList<>();
//        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
//        delegates.add(jwtAccessTokenConverter);
//        enhancerChain.setTokenEnhancers(delegates);
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userService)
//                .tokenStore(tokenStore) //配置令牌存储策略
//                .accessTokenConverter(jwtAccessTokenConverter)
//                .tokenEnhancer(enhancerChain);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("admin")
//                .secret(passwordEncoder.encode("admin123456"))
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(864000)
//                .redirectUris("http://localhost:9501/login") //单点登录时配置
//                .autoApprove(false) //自动授权配置
//                .scopes("all")
//                .authorizedGrantTypes("authorization_code","password","refresh_token");
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) {
//        security.tokenKeyAccess("isAuthenticated()"); // 获取密钥需要身份认证，使用单点登录时必须配置
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin")
                .secret(passwordEncoder.encode("admin123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7)
                .and()
                .withClient("portal-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService) //配置加载用户信息的服务
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair jwt = keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
        return jwt;
    }

}



