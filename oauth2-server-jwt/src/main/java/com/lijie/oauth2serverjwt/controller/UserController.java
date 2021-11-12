package com.lijie.oauth2serverjwt.controller;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

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
        String header = request.getHeader("Authorization");
        String token = StrUtil.subAfter(header, "bearer ", false);
        return Jwts.parser()
                .setSigningKey("wwwww".getBytes(StandardCharsets.UTF_8))//密钥是在JwtTokenStoreConfig里面配好的
                .parseClaimsJws(token)
                .getBody();
    }
}
