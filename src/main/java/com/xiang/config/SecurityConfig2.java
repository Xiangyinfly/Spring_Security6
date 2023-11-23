/*
package com.xiang.config;

import com.xiang.support.SmsCodeAuthenticationFilter;
import com.xiang.support.SmsCodeAuthenticationProvider;
import com.xiang.support.SmsCodeLoginConfigurer;
import com.xiang.support.SmsCodeLoginConfigurer2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//短信验证码登录第二种配置方式



@Configuration
public class SecurityConfig2 {
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http,
                                                          AuthenticationSuccessHandler authenticationSuccessHandler,
                                                          AuthenticationFailureHandler authenticationFailureHandler,
                                                          AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .csrf()
                .disable()
                //配置权限
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                //配置登录入口
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);



        SmsCodeLoginConfigurer2<HttpSecurity> smsCodeLoginConfigurer2 = new SmsCodeLoginConfigurer2<>();
        smsCodeLoginConfigurer2
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
        http.apply(smsCodeLoginConfigurer2);

        http.authenticationProvider(new SmsCodeAuthenticationProvider());
        return http.build();
    }
}
*/
