package com.xiang.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobilePhone = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();

        log.info("手机号【{}】验证码【{}】开始登录",mobilePhone,code);
        if (StringUtils.equals(code,"123456")) {
            return new SmsCodeAuthenticationToken(mobilePhone,code, Collections.emptyList());
        }

        throw new BadCredentialsException("验证码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
