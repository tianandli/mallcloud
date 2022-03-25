package com.lijie.oauth2serverdandiandenglu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 功能描述：配置资源服务器：拥有受保护资源的服务器，如果请求包含正确的访问令牌，可以访问资源
 *
 * @author: lijie
 * @date: 2021/11/12 9:29
 * @version: V1.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                //这里配置的路径，必须带着token才能访问，没有配置的，只需要登录了就能访问，不需要token
                .antMatchers("/**/**");//配置需要保护/限制的资源路径，我们这里配置让所有的都要去携带token
    }
}
