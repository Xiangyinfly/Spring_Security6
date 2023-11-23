/*
package com.xiang.support;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class SmsCodeLoginConfigurer2<B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<SmsCodeLoginConfigurer2<B>,B> {
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(B builder) throws Exception {
        SmsCodeAuthenticationFilter authenticationFilter = new SmsCodeAuthenticationFilter("/smsCodeLogin");
        //AuthenticationManager在构建的时候会把AuthenticationManager放到SharedObject中
        authenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        authenticationFilter.setAuthenticationDetailsSource(new WebAuthenticationDetailsSource());

        builder.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    public final SmsCodeLoginConfigurer2<B> successHandler(AuthenticationSuccessHandler successHandler) {
        this.authenticationSuccessHandler = successHandler;
        return this;
    }

    public final SmsCodeLoginConfigurer2<B> failureHandler(AuthenticationFailureHandler failureHandler) {
        this.authenticationFailureHandler = failureHandler;
        return this;
    }
}
*/
