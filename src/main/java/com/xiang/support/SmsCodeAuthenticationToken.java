package com.xiang.support;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 实现短信验证码登录
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;

    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
    }
    public SmsCodeAuthenticationToken(Object principal, Object credentials,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
