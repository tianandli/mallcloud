package com.lijie.oauth2serverdandiandenglu.controller;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.text.ParseException;

/**
 * 功能描述：登录接口，用于测试
 *
 * @author: lijie
 * @date: 2021/11/12 9:32
 * @version: V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {
        try {
            String header = request.getHeader("Authorization");
            String token = StrUtil.subAfter(header, "bearer ", false);
            JWSObject jwsObject = JWSObject.parse(token);//使用JWS来自动解析
            String userStr = jwsObject.getPayload().toString();
            return userStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "授权失败";
    }
}
