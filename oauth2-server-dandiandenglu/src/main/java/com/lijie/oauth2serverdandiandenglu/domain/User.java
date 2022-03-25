package com.lijie.oauth2serverdandiandenglu.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 功能描述：User实体类
 *
 * @author: lijie
 * @date: 2021/11/12 9:15
 * @version: V1.0
 */
public class User implements UserDetails {
    //要给前端返回什么，这里就封装什么属性
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;//这里一般是这个用户的权限集合

    public User(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
