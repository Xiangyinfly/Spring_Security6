package com.xiang.support;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SmsCodeLoginConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractAuthenticationFilterConfigurer<H, SmsCodeLoginConfigurer<H>, SmsCodeAuthenticationFilter> {

    public SmsCodeLoginConfigurer(String defaultLoginProcessingUrl) {
        super(new SmsCodeAuthenticationFilter(defaultLoginProcessingUrl), defaultLoginProcessingUrl);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }
}