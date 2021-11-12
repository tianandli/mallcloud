package com.lijie.oauth2serverjwt.config;

import com.lijie.oauth2serverjwt.component.JwtTokenEnhancer;
import com.lijie.oauth2serverjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

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

    //补充
    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        /**
         * 描述：JWT增强需要增加下面的内容
         */
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .tokenEnhancer(enhancerChain)//JWT增强需要加这个设置
                .tokenStore(tokenStore)//存储token有两种方式：存储进redis和存储进JWT，这里只学习采用存储进JWT的方式
                .accessTokenConverter(jwtAccessTokenConverter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("mallcloud")//配置client_id
                .secret(passwordEncoder.encode("mallcloud"))//配置client_secret
                .accessTokenValiditySeconds(3600)//配置访问token的有效期
                .refreshTokenValiditySeconds(864000)//配置刷新token的有效期
                .redirectUris("http://www.baidu.com")//配置redirect_uri，用于授权成功后跳转
                .scopes("all")//配置申请的权限范围
                //我们常用的也就是授权码和密码这俩种类型。建议使用password这种类型，好理解
                .authorizedGrantTypes("authorization_code","password");//配置grant_type，表示授权类型。
    }
}
