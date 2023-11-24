package com.xiang.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;
    public JwtAuthenticationToken(String userId) {
        super(Collections.emptyList());
        this.userId = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        //返回值为Object，可以为UserDetails类或自定义一个类实现该类的对象
        //此处为了方便，直接设置一个属性并返回
        return this.userId;
    }
}
